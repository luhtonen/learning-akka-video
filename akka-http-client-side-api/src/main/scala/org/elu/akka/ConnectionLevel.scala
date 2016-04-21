package org.elu.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Created by luhtonen on 21/04/16. */
object ConnectionLevel extends App {
  import JsonProtocol._
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[Http.OutgoingConnection]] =
    Http().outgoingConnection("api.ipify.org")

  val responseFuture = Source.single(HttpRequest(uri = "/?format=json"))
    .via(connectionFlow)
    .runWith(Sink.head)

  responseFuture map { res =>
    res.status match {
      case OK =>
        Unmarshal(res.entity).to[IpInfo].map { info =>
          println(s"The information for my ip is: $info")
          shutdown()
        }
      case _ =>
        Unmarshal(res.entity).to[String].map { body =>
          println(s"The response status is ${res.status} and response body is $body")
          shutdown()
        }
    }
  }

  def shutdown() = {
    Http().shutdownAllConnectionPools().onComplete { _ =>
      system.terminate()
    }
  }
}

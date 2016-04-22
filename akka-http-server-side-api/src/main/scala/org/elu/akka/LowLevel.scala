package org.elu.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, HttpResponse, Uri}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn

/** Created by luhtonen on 22/04/16. */
object LowLevel extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val serverSource = Http().bind(interface = "localhost", port = 8888)
  val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach {connection =>
    println("Accepted new connection from " + connection.remoteAddress)

    connection handleWithSyncHandler requestHandler
  }).run()

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(entity = HttpEntity("Hello Akka HTTP Server Side API - Low Level!"))

    case _ =>
      HttpResponse(404, entity = "Unknown resource!")
  }

  println(s"Server online at http://localhost:8888/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

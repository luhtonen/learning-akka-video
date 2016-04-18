package org.elu.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Flow, Source}

import scala.concurrent.Await
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/** Created by luhtonen on 18/04/16. */
object Stream extends App {
  implicit val actorSystem = ActorSystem()
  implicit val flowMaterializer = ActorMaterializer()

  // Source
  val input = Source(1 to 100)

  // Flow
  val normalize = Flow[Int].map(_ * 2)

  // Sink
  val output = Sink.foreach[Int](println)

  input.via(normalize).runWith(output).andThen {
    case _ =>
      Await.result(actorSystem.terminate(), 10.seconds)
  }
}

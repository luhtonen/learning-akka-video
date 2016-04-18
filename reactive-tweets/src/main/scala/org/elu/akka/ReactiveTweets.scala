package org.elu.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Flow, Source}
import twitter4j.Status

import scala.concurrent.ExecutionContext.Implicits.global

/** Created by luhtonen on 18/04/16. */
object ReactiveTweets extends App {
  implicit val actorSystem = ActorSystem()
  implicit val flowMaterializer = ActorMaterializer()

  val source = Source.fromIterator(() => TwitterClient.retrieveTweets("#Akka"))

  val normalize = Flow[Status].map { t =>
    Tweet(Author(t.getUser.getName), t.getText)
  }
  val sink = Sink.foreach[Tweet](println)

  source.via(normalize).runWith(sink).andThen {
    case _ =>
      actorSystem.terminate()
  }
}

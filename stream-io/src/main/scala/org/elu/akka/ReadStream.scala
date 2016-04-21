package org.elu.akka

import java.io.File

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Framing, FileIO}
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration._

/** Created by luhtonen on 21/04/16. */
object ReadStream extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  // read lines from a log file
  val logFile = new File("src/main/resources/log.txt")
  val source = FileIO.fromFile(logFile)
  // parse chunks of bytes into lines
  val flow = Framing.delimiter(ByteString(System.lineSeparator()),
    maximumFrameLength = 512, allowTruncation = true).map(_.utf8String)
  val sink = Sink.foreach(println)

  source.via(flow).runWith(sink).andThen {
    case _ => Await.result(actorSystem.terminate(), 10.seconds)
  }
}

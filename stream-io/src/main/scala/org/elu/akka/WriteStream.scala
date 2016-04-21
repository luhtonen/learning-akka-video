package org.elu.akka

import java.io.File

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.util.ByteString

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/** Created by luhtonen on 21/04/16. */
object WriteStream extends App {
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  // Source
  val source = Source(1 to 10000).filter(isPrime)
  // Sink
  val sink = FileIO.toFile(new File("target/prime.txt"))
  // file output sink
  val fileSink = Flow[Int]
    .map(i => ByteString(i.toString))
    .toMat(sink)(Keep.right)
  // console output sink
  val consoleSink = Sink.foreach[Int](println)

  // send primes to both file sink and console sink using graph API
  val g = RunnableGraph.fromGraph(GraphDSL.create(fileSink, consoleSink)(Keep.left) { implicit builder =>
    (file, console) =>
      import GraphDSL.Implicits._

      val broadCast = builder.add(Broadcast[Int](2))

      source ~> broadCast ~> file
                broadCast ~> console

      ClosedShape
  }).run()

  // ensure the output file is closed and the system shutdown upon completion
  g.onComplete {
    case Success(_) =>
      actorSystem.terminate()
    case Failure(e) =>
      println(s"Failure: ${e.getMessage}")
      actorSystem.terminate()
  }

  def isPrime(n: Int): Boolean = {
    if (n <= 1) false
    else if (n == 2) true
    else !(2 until n).exists(x => n % x == 0)
  }
}

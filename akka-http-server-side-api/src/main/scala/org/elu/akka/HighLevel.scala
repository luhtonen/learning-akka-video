package org.elu.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn

/** Created by luhtonen on 22/04/16. */
object HighLevel extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

/*
  path(RESOURCE_PATH) {
    method[get | put | post...]
    {
      complete {
        // return your response is case you accept this request.
      }
      reject {
        // return your response is case you reject this request.
      }
    }
  }
*/

  val route = path("") {
    get {
      complete("Hello Akka HTTP Server Side API - High Level")
    }
  }
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

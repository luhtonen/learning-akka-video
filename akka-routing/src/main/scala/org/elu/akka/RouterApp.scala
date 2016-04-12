package org.elu.akka

import akka.actor.{Props, ActorSystem}
import org.elu.akka.Worker.Work

/** Created by luhtonen on 12/04/16. */
object RouterApp extends App {

  val system = ActorSystem("router")

  system.actorOf(Props[Worker], "w1")
  system.actorOf(Props[Worker], "w2")
  system.actorOf(Props[Worker], "w3")
  system.actorOf(Props[Worker], "w4")
  system.actorOf(Props[Worker], "w5")

  val workers: List[String] = List(
    "/user/w1",
    "/user/w2",
    "/user/w3",
    "/user/w4",
    "/user/w5"
  )
  val routesGroup = system.actorOf(Props(classOf[RouterGroup], workers))

  routesGroup ! Work()
  routesGroup ! Work()

  Thread.sleep(100)

  system.terminate()
}

package org.elu.akka

import akka.actor.{Props, ActorSystem}
import akka.routing.{RandomGroup, FromConfig}
import org.elu.akka.Worker.Work

/** Created by luhtonen on 12/04/16. */
object Random extends App {

  println("### Random Router Pool")
  val system = ActorSystem("Random-Router")

  val routerPool = system.actorOf(FromConfig.props(Props[Worker]), "random-router-pool")

  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()

  Thread.sleep(100)

  println("### Random Router Group")

  system.actorOf(Props[Worker], "w1")
  system.actorOf(Props[Worker], "w2")
  system.actorOf(Props[Worker], "w3")

  val paths = List("/user/w1", "/user/w2", "/user/w3")

  val routerGroup = system.actorOf(RandomGroup(paths).props(), "random-router-group")

  routerGroup ! Work()
  routerGroup ! Work()
  routerGroup ! Work()
  routerGroup ! Work()

  Thread.sleep(100)
  system.terminate()
}

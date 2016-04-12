package org.elu.akka

import akka.actor.{Props, ActorSystem}
import akka.routing.{FromConfig, RoundRobinPool}
import org.elu.akka.Worker.Work

/** Created by luhtonen on 12/04/16. */
object RoundRobin extends App {

  val system = ActorSystem("Round-Robin-Router")
  val routerPool = system.actorOf(RoundRobinPool(3).props(Props[Worker]), "round-robin-pool")

  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()
  routerPool ! Work()

  Thread.sleep(100)

  system.actorOf(Props[Worker], "w1")
  system.actorOf(Props[Worker], "w2")
  system.actorOf(Props[Worker], "w3")

  val routerGroup = system.actorOf(FromConfig.props(), "round-robin-group")

  routerGroup ! Work()
  routerGroup ! Work()
  routerGroup ! Work()
  routerGroup ! Work()

  Thread.sleep(100)
  system.terminate()
}

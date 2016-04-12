package org.elu.akka

import akka.actor.{ActorSystem, Props}

/** Created by luhtonen on 12/04/16. */
object PersistentFSMApp extends App {
  import Account._

  val system = ActorSystem("persistent-fsm-actors")
  val account = system.actorOf(Props[Account])

  account ! Operation(1000, CR)
  account ! Operation(10, DR)

  Thread.sleep(1000)
  system.terminate()
}

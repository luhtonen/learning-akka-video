package org.elu.akka

import akka.actor.{ActorSystem, Props}

/** Created by luhtonen on 12/04/16. */
object Persistent extends App {
  import Counter._

  val system = ActorSystem("persistent-actors")
  val counter = system.actorOf(Props[Counter])

  counter ! Cmd(Increment(3))
  counter ! Cmd(Increment(5))
  counter ! Cmd(Decrement(3))
  counter ! "print"

  Thread.sleep(1000)
  system.terminate()
}

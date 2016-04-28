package org.elu.akka

import akka.actor.{PoisonPill, Props, ActorSystem}
import org.elu.akka.pattern._

/** Created by luhtonen on 28/04/16. */
object OrderTermination extends App {

  val system = ActorSystem("order-termination")
  val terminator = system.actorOf(Props(new Terminator(Props[Worker], 5)))
  val master = system.actorOf(Props(new Master(terminator)))

  master ! "hello world"
  master ! PoisonPill

  Thread.sleep(5000)
  system.terminate()
}

package org.elu.akka

import akka.actor.Actor

/** Created by luhtonen on 18/04/16. */
class Worker extends Actor {
  import Worker._

  def receive = {
    case Work =>
      println(s"I received Work Message and My ActorRef: ${self}")
      sender() ! Done
  }
}

object Worker {
  case object Work
  case object Done
}
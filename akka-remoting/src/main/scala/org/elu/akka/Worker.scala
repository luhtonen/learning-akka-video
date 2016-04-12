package org.elu.akka

import akka.actor.Actor

/** Created by luhtonen on 12/04/16. */
class Worker extends Actor {
  import Worker._

  def receive = {
    case msg: Work =>
      println(s"I received Work Message and My ActorRef: ${self}")
  }
}

object Worker {
  case class Work(message: String)
}
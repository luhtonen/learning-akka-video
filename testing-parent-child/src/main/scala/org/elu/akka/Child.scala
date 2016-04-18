package org.elu.akka

import akka.actor.{ActorRef, Actor}

/** Created by luhtonen on 18/04/16. */
class Child(parent: ActorRef) extends Actor {
  def receive = {
    case "ping" => parent ! "pong"
  }
}

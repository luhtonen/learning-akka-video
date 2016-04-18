package org.elu.akka

import akka.actor.{Actor, ActorRef, ActorRefFactory}

/** Created by luhtonen on 18/04/16. */
class Parent(childMaker: ActorRefFactory => ActorRef) extends Actor {
  val child = childMaker(context)
  var ponged = false

  def receive = {
    case "ping" => child ! "ping"
    case "pong" => ponged = true
  }
}

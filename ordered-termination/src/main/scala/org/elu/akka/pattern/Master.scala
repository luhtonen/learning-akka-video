package org.elu.akka.pattern

import akka.actor.{Actor, ActorRef}

/** Created by luhtonen on 28/04/16. */
class Master(terminator: ActorRef) extends Actor {
  import Terminator._

  // Ask for the kids
  override def preStart() {
    terminator ! GetChildren(self)
  }

  // Wait for the kids to show up
  def waiting: Receive = {
    case Children(kids) =>
      // become our initialized state
      context.become(initialized(kids))
  }

  // Do our normal business logic
  def initialized(kids: Iterable[ActorRef]): Receive = {
    case msg =>
      println(s"Cool, I got a message: $msg")
  }

  // Start waiting
  def receive = waiting
}

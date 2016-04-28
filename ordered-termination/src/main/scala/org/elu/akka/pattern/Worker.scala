package org.elu.akka.pattern

import akka.actor.Actor

/** Created by luhtonen on 28/04/16. */
class Worker extends Actor {
  override def preStart() {
    println("%s is running".format(self.path.name))
  }
  override def postStop() {
    println("%s has stopped".format(self.path.name))
  }
  def receive = {
    case msg =>
      println("Cool, I got a message: " + msg)
  }
}

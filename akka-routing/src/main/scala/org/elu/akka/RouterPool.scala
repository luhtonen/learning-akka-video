package org.elu.akka

import akka.actor.{Props, ActorRef, Actor}
import org.elu.akka.Worker.Work

/** Created by luhtonen on 12/04/16. */
class RouterPool extends Actor {

  var routees: List[ActorRef] = _

  override def preStart() = {
    routees = List.fill(5){
      context.actorOf(Props[Worker])
    }
  }

  def receive() = {
    case msg: Work =>
      println("I'm A Router and received a Message.....")
      routees(util.Random.nextInt(routees.size)) forward msg
  }
}

class RouterGroup(routees: List[String]) extends Actor {
  def receive = {
    case msg: Work =>
      println(s"I'm a Router Group and I receive Work Message....")
      context.actorSelection(routees(util.Random.nextInt(routees.size))) forward msg
  }
}
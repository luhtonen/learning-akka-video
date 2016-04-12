package org.elu.akka

import akka.actor.{ActorSystem, Props, ActorRef, Actor}
import org.elu.akka.Worker.Work

/** Created by luhtonen on 12/04/16. */
class Router extends Actor {

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

object Router extends App {
  val system = ActorSystem("router")
  val router = system.actorOf(Props[Router])

  router ! Work()
  router ! Work()
  router ! Work()

  Thread.sleep(100)

  system.terminate()
}
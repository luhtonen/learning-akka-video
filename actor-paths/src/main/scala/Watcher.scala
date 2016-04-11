package org.elu.akka

import akka.actor._

class Watcher extends Actor {

  var counterRef: ActorRef = _
  val selection = context.actorSelection("/user/counter")

  selection ! Identify(None)

  def receive = {
    case ActorIdentity(_, Some(ref)) =>
      println(s"Actor Reference for counter is ${ref}")
    case ActorIdentity(_, None) =>
      println("Actor selection for actor doesn't live :(")
  }
}

object Watch extends App {
  val system = ActorSystem("Watch-actor-selection")

  val counter = system.actorOf(Props[Counter], "counter")
  val watcher = system.actorOf(Props[Watcher], "watcher")

  Thread.sleep(1000)

  system.terminate()
}

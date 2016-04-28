package org.elu.akka

import java.util.Date

import akka.actor.{PoisonPill, Props, ActorSystem, Actor}
import org.elu.akka.pattern.{Reaper, ReaperWatched}

class Target extends Actor with ReaperWatched {
  def receive = {
    case msg =>
      println(s"[${new Date().toString}]I received a message: $msg")
  }
}

object ShutdownApp extends App {
  val system = ActorSystem("shutdown")
  val reaper = system.actorOf(Props[Reaper], Reaper.name)
  val target = system.actorOf(Props[Target], "target")

  target ! "Hello World"
  target ! PoisonPill
}

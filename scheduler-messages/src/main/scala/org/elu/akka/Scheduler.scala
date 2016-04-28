package org.elu.akka

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.Actor
import scala.concurrent.duration._
import scala.language.postfixOps

class ScheduleInConstrutor extends Actor {
  import context._

  val tick = context.system.scheduler.schedule(500 millis, 1.second, self, "tick")

  override def postStop() = tick.cancel()

  def receive = {
    case "tick" =>
      println(s"Cool! I got tick message at ${new SimpleDateFormat().format(new Date())}")
  }
}

class ScheduleInReceive extends Actor {
  import context._

  override def preStart() = context.system.scheduler.scheduleOnce(500 millis, self, "tick")

  // override postRestart so we don't call preStart and schedule a new message
  override def postRestart(reason: Throwable) = {}

  def receive = {
    case "tick" =>
      println(s"Cool! I got tick message at ${new SimpleDateFormat().format(new Date())}")
      // send another periodic tick after the specified delay
      context.system.scheduler.scheduleOnce(1000 millis, self, "tick")
  }
}
package org.elu.akka

import akka.actor.{Props, ActorSystem}

/** Created by luhtonen on 28/04/16. */
object SchedulingMessages extends App {

  val system = ActorSystem("Scheduling-Messages")

//  val scheduler = system.actorOf(Props[ScheduleInConstrutor], "schedule-in-constructor")

  val scheduler = system.actorOf(Props[ScheduleInReceive], "schedule-in-receive")

  Thread.sleep(5000)
  system.terminate()
}

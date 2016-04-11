package org.elu.akka

import akka.actor.{ActorSystem, Props, Actor}
import org.elu.akka.MusicController.{Stop, Play}
import org.elu.akka.MusicPlayer.{StartMusic, StopMusic}

// Music Controller Messages
object MusicController {
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  def props = Props[MusicController]
}

// Music Controller
class MusicController extends Actor {
  def receive = {
    case Play => println("Music Started .....")
    case Stop => println("Music Stopped .....")
  }
}

// Music Player Messages
object MusicPlayer {
  sealed trait PlayMsg
  case object StopMusic extends PlayMsg
  case object StartMusic extends PlayMsg
}

// Music Player
class MusicPlayer extends Actor {
  def receive = {
    case StopMusic => println("I don't want to stop music")
    case StartMusic =>
      val controller = context.actorOf(MusicController.props, "controller")
      controller ! Play
    case _ => println("Unknown Message")
  }
}

object Creation extends App {
  // Create the 'creation' actor system
  val system = ActorSystem("creation")

  // Create the 'MusicPlayer' actor
  val player = system.actorOf(Props[MusicPlayer], "player")

  // Send StartMusic Message to actor
  player ! StartMusic

  // Send StopMusic Message to actor

  // shutdown system
  system.shutdown()
}
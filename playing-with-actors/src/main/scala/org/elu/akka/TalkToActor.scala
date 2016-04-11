package org.elu.akka

import akka.actor.{ActorSystem, Props, Actor, ActorRef}
import akka.pattern.ask
import akka.util.Timeout
import org.elu.akka.Checker.{BlackUser, CheckUser, WhiteUser}
import org.elu.akka.Recorder.NewUser
import org.elu.akka.Storage.AddUser

import scala.concurrent.duration._
import scala.language.postfixOps

case class User(username: String, email: String)

object Recorder {
  sealed trait RecorderMsg
  // Recorder Messages
  case class NewUser(user: User) extends RecorderMsg

  def props(checker: ActorRef, storage: ActorRef) = Props(new Recorder(checker, storage))
}

object Checker {
  sealed trait CheckerMsg
  // Checker Messages
  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckerResponse
  // Checker Responses
  case class BlackUser(user: User) extends CheckerResponse
  case class WhiteUser(user: User) extends CheckerResponse
}

object Storage {
  sealed trait StorageMsg
  // Storage Messages
  case class AddUser(user: User) extends StorageMsg
}

class Storage extends Actor {
  var users = List.empty[User]

  def receive = {
    case AddUser(user: User) =>
      println(s"Storage: $user added")
      users = user :: users
  }
}

class Checker extends Actor {
  val blackList = List(
    User("Adam", "adam@mail.com")
  )

  def receive = {
    case CheckUser(user: User) if blackList.contains(user) =>
      println(s"Checker: $user in the blacklist")
      sender() ! BlackUser(user)
    case CheckUser(user: User) =>
      println(s"Checker: $user is not in the blacklist")
      sender() ! WhiteUser(user)
  }
}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(5 seconds)

  def receive = {
    case NewUser(user) =>
      checker ? CheckUser(user) map {
        case WhiteUser(user) =>
          storage ! AddUser(user)
        case BlackUser(user) =>
          println(s"Recorder $user is in the blacklist")
      }
  }
}

object TalkToActor extends App {

  // Create the 'talk-to-actor' actor system
  val system = ActorSystem("talk-to-actor")

  // Create the 'checker' actor
  val checker = system.actorOf(Props[Checker], "checker")

  // Create the 'storage' actor
  val storage = system.actorOf(Props[Storage], "storage")

  // Create the 'recorder' actor
  val recorder = system.actorOf(Recorder.props(checker, storage), "recorder")

  //send NewUser Message to Recorder
  recorder ! Recorder.NewUser(User("Jon", "jon@packt.com"))

  Thread.sleep(100)

  //shutdown system
  system.terminate()

}
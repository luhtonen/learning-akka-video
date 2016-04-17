package org.elu.akka

import akka.actor.Actor

/** Created by luhtonen on 17/04/16. */
class Counter extends Actor {
  import Counter._

  var count: Int = 0

  def receive = {
    case Increment =>
      count += 1
    case Decrement =>
      count -= 1
    case GetCount =>
      sender ! count
  }
}

object Counter {
  case object Increment
  case object Decrement
  case object GetCount
}
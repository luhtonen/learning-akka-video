package org.elu.akka

import akka.actor.Actor
import org.elu.akka.Counter.{Dec, Inc}

object Counter {
  final case class Inc(num: Int)
  final case class Dec(num: Int)
}

class Counter extends Actor {

  var count = 0

  def receive = {
    case Inc(x) =>
      count += x
    case Dec(x) =>
      count -= x
  }
}

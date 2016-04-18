package org.elu.akka

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestKit}
import org.scalatest.{MustMatchers, BeforeAndAfterAll, FlatSpecLike}

/** Created by luhtonen on 18/04/16. */
class ChildSpec extends TestKit(ActorSystem("test-system"))
  with ImplicitSender
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers {

  override def afterAll = {
    TestKit.shutdownActorSystem(system)
  }

  "Child Actor" should "send pong message when receive ping message" in {
    val parent = TestProbe()

    val child = system.actorOf(Props(new Child(parent.ref)))

    child ! "ping"

    parent.expectMsg("pong")
  }
}

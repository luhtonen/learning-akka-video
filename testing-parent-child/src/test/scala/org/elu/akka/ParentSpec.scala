package org.elu.akka

import akka.actor.{Props, ActorRefFactory, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestKit}
import org.scalatest.{MustMatchers, BeforeAndAfterAll, FlatSpecLike}

/** Created by luhtonen on 18/04/16. */
class ParentSpec extends TestKit(ActorSystem("test-system"))
  with ImplicitSender
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers {

  override def afterAll = {
    TestKit.shutdownActorSystem(system)
  }

  "Parent" should "send pint message to child when receive ping message" in {
    val child = TestProbe()
    val childMaker = (_: ActorRefFactory) => child.ref
    val parent = system.actorOf(Props(new Parent(childMaker)))

    parent ! "ping"

    child.expectMsg("ping")
  }
}

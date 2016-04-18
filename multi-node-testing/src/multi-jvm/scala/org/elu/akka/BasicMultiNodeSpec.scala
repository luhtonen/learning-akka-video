package org.elu.akka

import akka.remote.testkit.MultiNodeSpecCallbacks
import org.scalatest.{FlatSpecLike, MustMatchers, BeforeAndAfterAll}

/** Created by luhtonen on 18/04/16. */
trait BasicMultiNodeSpec extends MultiNodeSpecCallbacks
  with FlatSpecLike
  with MustMatchers
  with BeforeAndAfterAll {

  override def beforeAll = multiNodeSpecBeforeAll()

  override def afterAll = multiNodeSpecAfterAll()
}

package org.elu.akka

import akka.remote.testkit.MultiNodeConfig


/** Created by luhtonen on 18/04/16. */
object MultiNodeSampleConfig extends MultiNodeConfig {
  val node1 = role("node1")
  val node2 = role("node2")
}

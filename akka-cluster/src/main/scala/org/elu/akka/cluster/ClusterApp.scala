package org.elu.akka.cluster

import org.elu.akka.commons.Add

/** Created by luhtonen on 13/04/16. */
object ClusterApp extends App {

  //initiate frontend node
  Frontend.initiate()

  //initiate three nodes from backend
  Backend.initiate(2552)
  Backend.initiate(2560)
  Backend.initiate(2561)

  Thread.sleep(10000)

  Frontend.getFrontend ! Add(2, 4)
}

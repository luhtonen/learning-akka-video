package org.elu.akka.db

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global

/** Created by luhtonen on 22/04/16. */
object MongoDB {

  val config = ConfigFactory.load()
  val database = config.getString("mongodb.database")
  val servers = config.getStringList("mongodb.servers").asScala

  println(s"about to create database connection to servers [$servers] and database $database")

  val driver = new MongoDriver
  val connection = driver.connection(servers)

  val db = connection.database(database)
}

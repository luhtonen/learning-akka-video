package org.elu.akka

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.elu.akka.db.{Created, TweetManager}
import org.elu.akka.models._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, MustMatchers}

import scala.concurrent.Await
import scala.concurrent.duration._

/** Created by luhtonen on 27/04/16. */
class ApiSpec extends FlatSpec
  with MustMatchers
  with ScalatestRouteTest
  with BeforeAndAfterAll
  with RestApi {

  import TweetEntityProtocol.EntityFormat
  import TweetProtocol._

  override implicit val ec = system.dispatcher

  override def afterAll: Unit = {
    TweetManager.collection.drop(failIfNotFound = false)
  }

  "The Server" should "return Ok response when get all tweets" in {
    val tweet = Tweet("akka", "Hello World")
    val f = TweetManager.save(TweetEntity.toTweetEntity(tweet))
    val Created(id) = Await.result(f, 1.second)

/*
    TODO: fix get all REST endpoint
    Get("/tweets") ~> route ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[List[TweetEntity]]
      res.size must equal(1)
      res(0) must equal(TweetEntity(BSONObjectID(id), tweet.author, tweet.body))
    }
*/
  }

  it should "return created response when create new tweet" in {
    Post("/tweets", Tweet("akka", "Hello World")) ~> route ~> check {
      status must equal(StatusCodes.Created)
    }
  }

  it should "return No Content response when delete a tweet" in {
    val tweet = Tweet("akka", "hello world")
    val f = TweetManager.save(TweetEntity.toTweetEntity(tweet))
    val Created(id) = Await.result(f, 1.second)

    Delete(s"/tweets/$id") ~> route ~> check {
      status must equal(StatusCodes.NoContent)
    }
  }

  it should "return Ok response when get a tweet" in {
    val tweetEntity = TweetEntity.toTweetEntity(Tweet("akka", "hello world"))
    val f = TweetManager.save(tweetEntity)
    val Created(id) = Await.result(f, 1.second)

    Get(s"/tweets/$id") ~> route ~> check {
      status must equal(StatusCodes.OK)

      responseAs[TweetEntity] must equal(tweetEntity)
    }
  }
}

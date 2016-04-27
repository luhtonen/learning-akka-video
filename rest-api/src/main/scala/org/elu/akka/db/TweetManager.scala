package org.elu.akka.db

import org.elu.akka.models.TweetEntity
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/** Created by luhtonen on 22/04/16. */
object TweetManager {
  import MongoDB._
  import TweetEntity._

  val collection: BSONCollection = Await.result(db.map(_.collection("tweets")), 10.seconds)

  def save(tweetEntity: TweetEntity)(implicit ec: ExecutionContext) =
    collection.insert(tweetEntity).map(_ => Created(tweetEntity.id.stringify))

  def findById(id: String)(implicit ec: ExecutionContext) =
    collection.find(queryById(id)).one[TweetEntity]

  def deleteById(id: String)(implicit ec: ExecutionContext) =
    collection.remove(queryById(id)).map(_ => Deleted)

  def find(implicit ec: ExecutionContext) =
    collection.find(emptyQuery).cursor[BSONDocument]().collect[List]()

  private def queryById(id: String) = BSONDocument("_id" -> BSONObjectID(id))

  private def emptyQuery = BSONDocument()
}

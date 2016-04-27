package org.elu.akka.models

import spray.json.DefaultJsonProtocol

case class Tweet(author: String, body: String)

object TweetProtocol extends DefaultJsonProtocol {
  implicit val format = jsonFormat2(Tweet.apply)
}

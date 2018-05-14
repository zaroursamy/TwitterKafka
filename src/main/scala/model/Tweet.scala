package model

import twitter4j.Status

import scala.util.Try

case class Tweet(user: UserTweet, geoLocation: Geo, tweet: String) extends Event

object Tweet{
  def fromStatus(status: Status): Tweet = {

    val g = status.getGeoLocation
    val (latitude, longitude) = (Try(g.getLatitude).toOption, Try(g.getLongitude).toOption)
    Tweet(
      user = UserTweet.fromStatus(status),
      geoLocation = Geo(latitude, longitude),
      tweet = status.getText
    )
  }
}
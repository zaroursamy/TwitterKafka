package model

import twitter4j.{GeoLocation, Status, User}

import scala.util.Try

/**
  * Created by zarour on 21/12/2017.
  */

sealed trait Event

case class Geo(lat:Option[Double],
               long:Option[Double])

case class Tweet(user: UserTweet, geoLocation: Geo, tweet: String) extends Event

object Tweet{
  def fromStatus(status: Status)={

    val g = status.getGeoLocation
    val (latitude, longitude) = (Try(g.getLatitude).toOption, Try(g.getLongitude).toOption)
    Tweet(
      UserTweet.fromStatus(status),
      Geo(latitude, longitude),
      status.getText
    )
  }
}

case class UserTweet(description:Option[String],
                     id:Long,
                     location:String,
                     lang:String,
                     name:String,
                     nbFriends:Int
                    )

object UserTweet{
  def fromStatus(status:Status): UserTweet ={
    val user = status.getUser
    import user._
    UserTweet(
      description = Option(getDescription).flatMap(Option(_)),
      id = getId,
      location = getLocation,
      lang = getLang,
      name = getName,
      nbFriends = getFriendsCount
    )
  }
}

package model

import twitter4j.{GeoLocation, Status, User}

/**
  * Created by zarour on 21/12/2017.
  */

sealed trait Event

case class Geo(lat:Double,
               long:Double)

case class Tweet(user: UserTweet, geoLocation: Geo, tweet: String) extends Event

object Tweet{
  def fromStatus(status: Status)={
    Tweet(
      UserTweet.fromUser(status),
      Geo(status.getGeoLocation.getLatitude, status.getGeoLocation.getLongitude),
      status.getText
    )
  }
}

case class UserTweet(description:String,
                     id:Long,
                     location:String,
                     lang:String,
                     name:String,
                     nbFriends:Int
                    )

object UserTweet{
  def fromUser(status:Status): UserTweet = {
    val user = status.getUser
    import user._
    UserTweet(
      description = getDescription,
      id = getId,
      location = getLocation,
      lang = getLang,
      name = getName,
      nbFriends = getFriendsCount
    )
  }
}

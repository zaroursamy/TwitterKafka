package model

import twitter4j.Status

/**
  * Created by zarour on 21/12/2017.
  */

case class UserTweet(description:Option[String],
                     id:Long,
                     location:String,
                     lang:String,
                     name:String,
                     nbFriends:Int
                    )

object UserTweet{
  def fromStatus(status:Status): UserTweet = {
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

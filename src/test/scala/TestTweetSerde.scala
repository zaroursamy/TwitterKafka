import model.{Geo, Tweet, UserTweet}
import org.scalatest.FunSuite
import utils.TweetSerde

/**
  * Created by zarour on 21/12/2017.
  */


class TestTweetSerde extends FunSuite {
  test("to json") {

    val userTweet = UserTweet("desc", 0L, "paris", "fr", "samas", 2)
    val tweet = Tweet(userTweet, Geo(0, 0), "this is my first tweet")

    val json = TweetSerde.toJson(tweet)
    val str = json.toString()

    println(s"json: ${json.toString}")
    println(s"tweet: ${tweet}")

    assert(TweetSerde.toJson(tweet) == json)
    assert(TweetSerde.fromJson(str).right.get == tweet)
  }


}



package utils

import io.circe.Decoder.Result
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, _}
import model.{Geo, Tweet, UserTweet}



/**
  * Created by zarour on 21/12/2017.
  */

trait ProjectEncoderDecoder {

  implicit val userDecoder: Decoder[UserTweet] = deriveDecoder[UserTweet]
  implicit val userEncoder: Encoder[UserTweet] = deriveEncoder[UserTweet]
  implicit val geoDecoder: Decoder[Geo] = deriveDecoder[Geo]
  implicit val geoEncoder: Encoder[Geo] = deriveEncoder[Geo]
  implicit val tweetDecoder: Decoder[Tweet] = deriveDecoder[Tweet]
  implicit val tweetEncoder: Encoder[Tweet] = deriveEncoder[Tweet]

}

object TweetSerde extends ProjectEncoderDecoder {

  def toJson(tweet: Tweet): Json = {
    import io.circe.syntax._
    tweet.asJson
  }


  def fromJson(s: String): Result[Tweet] = {
    import cats.syntax.either._
    import io.circe._
    import io.circe.parser._

    val json: Json = parse(s).getOrElse(Json.Null)
    json.as[Tweet]
  }
}

object TwitterIds{
  def userId: Map[String, Long] = Map(
    "pnl" -> 2396018036L,
    "jul" -> 4113487139L,
    "booba" -> 179530581L
  )
}

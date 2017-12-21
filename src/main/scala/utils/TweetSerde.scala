package utils

import java.time.Instant
import java.util.UUID

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.decoding._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, _}
import model.{Geo, Tweet, UserTweet}
import shapeless.the



/**
  * Created by zarour on 21/12/2017.
  */

trait ProjectEncoderDecoder {

  implicit val encodeUUID: Encoder[UUID] = Encoder.encodeString.contramap[UUID](_.toString)
  implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)
  implicit val decodeUUID: Decoder[UUID] = Decoder.decodeString.map[UUID](UUID.fromString)
  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.map[Instant](Instant.parse)
  implicit val userDecoder: Decoder[UserTweet] = deriveDecoder[UserTweet]
  implicit val userEncoder: Encoder[UserTweet] = deriveEncoder[UserTweet]
  implicit val geoDecoder: Decoder[Geo] = deriveDecoder[Geo]
  implicit val geoEncoder: Encoder[Geo] = deriveEncoder[Geo]
  implicit val tweetDecoder: Decoder[Tweet] = deriveDecoder[Tweet]
  implicit val tweetEncoder: Encoder[Tweet] = deriveEncoder[Tweet]

}

object TweetSerde extends ProjectEncoderDecoder {


  implicit val customConfig: Configuration = Configuration.default.withDefaults.withDiscriminator("_typehint")

  lazy val decoder: ConfiguredDecoder[Tweet] = the[ConfiguredDecoder[Tweet]]


  def toJson(tweet: Tweet): Json = {
    import io.circe.syntax._
    tweet.asJson
  }


  def fromJson(s: String) = {
    import cats.syntax.either._
    import io.circe._
    import io.circe.parser._

    val json: Json = parse(s).getOrElse(Json.Null)
    json.as[Tweet]
  }
}

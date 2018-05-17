package main

import java.util.Properties
import model.Tweet
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.{KStream, Predicate, ValueMapper}
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
import utils.TweetSerde

import scala.util.{Failure, Success, Try}

/**
  * Created by zarour on 20/12/2017.
  */

object StreamRawToTweet extends App {

  val props = new Properties
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-raw-tweet")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass())
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass())

  val builder = new StreamsBuilder

  val topology = builder.build()

  val source: KStream[String, String] = builder.stream("tweets.raw")

  val tweetStream: KStream[String, Tweet] = source.mapValues[Option[Tweet]](new ValueMapper[String, Option[Tweet]] {
    override def apply(value: String): Option[Tweet] = Try(TweetSerde.fromJson(value)) match {
      case Success(tweet) => tweet match {
        case Right(t) => Some(t)
        case Left(_) => None
      }
      case Failure(f) => f.printStackTrace(); None
    }
  })
    .filter(new Predicate[String, Option[Tweet]] {
      override def test(key: String, value: Option[Tweet]): Boolean = value.nonEmpty
    })
    .mapValues[Tweet](new ValueMapper[Option[Tweet], Tweet] {
      override def apply(value: Option[Tweet]): Tweet = value.get
    })

  tweetStream.to("tweets.model")

  val streams = new KafkaStreams(topology, props)

  streams.start()




}



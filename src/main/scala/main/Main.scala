package main

import java.util
import java.util.Properties

import config.TwitterConfig
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.streams.StreamsConfig
import twitter4j._


/**
  * Created by zarour on 13/04/2017.
  */

object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey(TwitterConfig.getKeys.appKey)
    .setOAuthConsumerSecret(TwitterConfig.getKeys.consumerSecret)
    .setOAuthAccessToken(TwitterConfig.getKeys.accessToken)
    .setOAuthAccessTokenSecret(TwitterConfig.getKeys.tokenSecret)
    .build

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) { println(status.getText) }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }
}

object KafkConsumerTweet extends App{
  val props = new Properties()

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

  val consumer = new KafkaConsumer[String, String](props)
  val topic = util.Collections.singletonList("monTopic")
  consumer.subscribe(topic)

  import scala.collection.JavaConverters._
  while (true) {
    val records: ConsumerRecords[String, String] = consumer.poll(100)
    for (record <- records.asScala) {
      println(s"records: $record")
    }
  }
}

object StreamTwitter extends App{
  // DEFINITION DES PROPERTIES
  val props = new Properties()

  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

    val listener = new StatusListener {
      override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

      override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {}

      override def onStatus(status: Status): Unit = {

        val tweet = status.getText
        val message = new ProducerRecord[String, String]("tweets", null, tweet)
        producer.send(message)
        Thread.sleep(10000)

      }

      override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}

      override def onStallWarning(warning: StallWarning): Unit = {}

      override def onException(ex: Exception): Unit = ex.printStackTrace()
    }

    val twitterStream = new TwitterStreamFactory(Util.config).getInstance()
    twitterStream.addListener(listener)


    val toFilter: FilterQuery = new FilterQuery()
    toFilter.follow(807095L) // nytimes

    twitterStream.filter(toFilter)


}
package main

import java.util

import config.{KafkaConfig, TwitterConfig, TwitterKafkaProducer}
import kafka.{TwitterKafkaConsumer, TwitterKafkaProducer}
import org.apache.kafka.clients.consumer.ConsumerRecords
import utils.{KafkaUtil, TwitterIds}


/**
  * Created by zarour on 13/04/2017.
  */

object TweetConsumer extends App {

  val consumer = TwitterKafkaConsumer.getTwitterConsummer(KafkaConfig.consummerProps)

  val topic = util.Collections.singletonList("tweets.raw")
  consumer.subscribe(topic)

  import scala.collection.JavaConverters._

  while (true) {
    val records: ConsumerRecords[String, String] = consumer.poll(100)
    for (record <- records.asScala) {
      println(s"records: $record")
    }
  }
}

object TweetProducer extends App {

  val producer = TwitterKafkaProducer.getTwitterProducer(KafkaConfig.producerProps)

  val listener = KafkaUtil.simpleStatusListener(producer)

  val twitterStream = new TwitterStreamFactory(TwitterConfig.config).getInstance()
  twitterStream.addListener(listener)


  val toFilter: FilterQuery = new FilterQuery()
  toFilter.follow(TwitterIds.userId.values.toSeq: _*) // nytimes

  twitterStream.filter(toFilter)

}


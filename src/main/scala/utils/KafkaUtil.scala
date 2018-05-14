package utils

import model.Tweet
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}
import scala.util.{Failure, Success, Try}

object KafkaUtil {

  def simpleStatusListener(producer: KafkaProducer[String, String]) = new StatusListener {
    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {}

    override def onStatus(status: Status): Unit = {

      val tweetModel: Tweet = Tweet.fromStatus(status)

      Try(TweetSerde.toJson(tweetModel).toString) match {
        case Success(t) =>
          val message = new ProducerRecord[String, String]("tweets.raw", null, t)
          producer.send(message)
          Thread.sleep(1000) // every second
        case Failure(f) => f.printStackTrace()
      }

    }

    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}

    override def onStallWarning(warning: StallWarning): Unit = {}

    override def onException(ex: Exception): Unit = ex.printStackTrace()
  }

}

package kafka

import java.util.Properties

object TwitterKafkaProducer {
  def getTwitterProducer(consummerProps: Properties) = new KafkaProducer[String, String](consummerProps)
}

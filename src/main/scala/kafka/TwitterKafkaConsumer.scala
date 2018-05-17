package kafka

import java.util.Properties

object TwitterKafkaConsumer {
  def getTwitterConsummer(consummerProps: Properties) = new KafkaConsumer[String, String](consummerProps)
}

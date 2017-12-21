package main

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}

/**
  * Created by zarour on 20/12/2017.
  */
object StreamTest extends App {

 val props = new Properties
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass())
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass())

  val builder = new StreamsBuilder

  builder.stream("tweets").to("tweetsFiltered")

  val topology = builder.build()
  println(topology.describe())

  val streams = new KafkaStreams(topology, props)

}

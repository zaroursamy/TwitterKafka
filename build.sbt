name := "Twitter"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Maven central" at "http://repo1.maven.org/maven2/"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "org.apache.kafka" % "kafka_2.11" % "1.0.0",
  "org.apache.kafka" % "kafka-streams" % "1.0.0"
)

name := "Twitter"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Maven central" at "http://repo1.maven.org/maven2/"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "org.apache.kafka" % "kafka_2.11" % "1.0.0",
  "org.apache.kafka" % "kafka-streams" % "1.0.0",
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test

)

val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "circe-core",
  "circe-optics",
  "circe-generic",
  "circe-parser",
  "circe-generic-extras"
).map(x => "io.circe" %% x % circeVersion)
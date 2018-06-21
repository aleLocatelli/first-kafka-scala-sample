name := "kafka-sample-2"

version := "0.1"

scalaVersion := "2.12.6"

val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka_2.12" % "1.0.0"
)

libraryDependencies += "org.apache.avro" % "avro" % "1.8.2"
libraryDependencies += "io.confluent" % "kafka-avro-serializer" % "4.1.0"
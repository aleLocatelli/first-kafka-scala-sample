package org.conf

import java.util.{Properties, UUID}

import io.confluent.kafka.serializers.{KafkaAvroDeserializer, KafkaAvroSerializer}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

object ConfluentProperties {

  val schemaRegistryUrl = "http://localhost:8081"
  val bootstrapServers = "localhost:9092"

  def getProducer() : Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "Sensor-x1-Producer")
    props.put("schema.registry.url", schemaRegistryUrl)
    props.put(ProducerConfig.ACKS_CONFIG, "all")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
    props
  }

  def getConsumer(): Properties = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getCanonicalName)
    //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getCanonicalName)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[KafkaAvroDeserializer])//"io.confluent.kafka.serializers.KafkaAvroSerializer")
    // TODO Next two properties are good only for local tests
    props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString)
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "my-app")
    props.put("schema.registry.url", schemaRegistryUrl)
    props
  }
}

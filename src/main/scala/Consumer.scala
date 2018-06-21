import kafka.utils.Logging
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.conf.ConfluentProperties
import org.model.SensorHeartbeat

class Consumer(topic: List[String]) extends Logging {

  val consumer = new KafkaConsumer[String, SensorHeartbeat](ConfluentProperties.getConsumer())
  consumer.subscribe(topic.asJava)

  def receiveMessages(): Unit = {
    while (true) {
      val records: ConsumerRecords[String, SensorHeartbeat] = consumer.poll(1000)
      println(s"######### Poll with empty result: ${records.isEmpty}")
      records.asScala.foreach(record => println(s"Received message: $record"))
    }
  }

}

object Consumer extends App {

  val consumer = new Consumer(List("Sensor1-heartbeat","Sensor4-heartbeat","Sensor2-heartbeat","Sensor3-heartbeat"))
  consumer.receiveMessages()

}
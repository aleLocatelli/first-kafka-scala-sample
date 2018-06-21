import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.conf.ConfluentProperties
import org.model.SensorHeartbeat

object Producer extends App {

  val sensors =
    Array[SensorInfo](
      SensorInfo("Sensor1", "Sensor1-heartbeat"),
      SensorInfo("Sensor2", "Sensor2-heartbeat"),
      SensorInfo("Sensor3", "Sensor3-heartbeat"),
      SensorInfo("Sensor4", "Sensor4-heartbeat")
    )

  val kafkaProducer = new KafkaProducer[String, SensorHeartbeat](ConfluentProperties.getProducer())

  val messages: Seq[(String, String, SensorHeartbeat)] =
    (0 to 3).flatMap(item => {

      val sensorItem = sensors(item)
      val sensorId : CharSequence = sensorItem.sensorId
      val sensorTopic = sensorItem.topic

      (0 to 5).map(messageItem =>{

        val heartbeatBuilder = SensorHeartbeat.newBuilder()
        heartbeatBuilder.setSensorId(sensorId)

        val key = s"$sensorTopic-$messageItem"
        val message = heartbeatBuilder.build()

        (sensorTopic, key, message)
      })
    })

  messages.foreach({ case (messageTopic: String, messageKey: String, message: SensorHeartbeat) => {
    val producerRecord =
      new ProducerRecord[String, SensorHeartbeat](messageTopic, messageKey, message)

    kafkaProducer.send(producerRecord)
  }})

  kafkaProducer.flush()
}

case class SensorInfo(sensorId: String, topic: String)
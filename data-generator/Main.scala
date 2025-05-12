import scala.util.Random
import java.util.{Properties,UUID}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig}
import org.apache.kafka.common.serialization.StringSerializer
import java.time.Instant


object Main {
    def main(args: Array[String]): Unit = {
        val topic = "sensor-data"
        val brokers = "localhost:9092"

        val props = new Properties()
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

        val producer = new KafkaProducer[String, String](props)

        println(s"Sending data to Kafka topic [$topic]... Press Ctrl+C to stop.")

        while (true) {
            val sensorId = s"sensor-${Random.nextInt(100)}"
            val temperature = 15 + Random.nextGaussian() * 10
            val timestamp = Instant.now().toString

            val json = 
                s"""
                |{
                |  "sensorId": "$sensorId",
                |  "temperature": ${"%.2f".format(temperature)},
                |  "timestamp": "$timestamp"
                |}
                """.stripMargin.replaceAll("\n", "")

            val record = new ProducerRecord[String, String](topic, sensorId, json)
            producer.send(record, (metadata, exception) => {
                if (exception != null) {
                    println(s"Error sending message: ${exception.getMessage}")
                } else {
                    println(s"Sent message to topic ${metadata.topic()} partition ${metadata.partition()} offset ${metadata.offset()}")
                }
            })
            Thread.sleep(1000) // Sleep for 1 second before sending the next message

        }
    }
}
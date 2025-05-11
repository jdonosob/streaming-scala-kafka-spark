import scala.util.Random
import java.util.{Properties,UUID}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig}


object Main {
    def main(args: Array[String]): Unit = {
        val topic = "sensor-data"
        val brokers = "localhost:9092"

        val props = new Properties()




        val random = new Random()
        val numRows = 1000000
        val numCols = 10
        val data = Array.fill(numRows, numCols)(random.nextDouble())

        // Print the first 10 rows of the generated data
        for (i <- 0 until 10) {
            println(data(i).mkString(", "))
        }
    }
}
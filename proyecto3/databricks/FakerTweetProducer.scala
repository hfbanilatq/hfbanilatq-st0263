// Databricks notebook source
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import java.util.{Properties, Random}

// COMMAND ----------


class FakeTweetReceiver(bootstrapServers: String, topic: String) extends Receiver[Unit](StorageLevel.MEMORY_AND_DISK_2) {
  def onStart(): Unit = {
    new Thread("Fake Tweet Generator") {
      override def run(): Unit = generateAndSendTweets()
    }.start()
  }

  def onStop(): Unit = {
    // Implementar lógica para limpiar recursos si es necesario
  }

// Generador de nombres y mensajes aleatorios
  val names = Array("Alice", "Bob", "Carol", "David")
  val topics = Array("tecnología", "deporte", "música", "viajes", "cocina")
  val frasesPositivas = Array("¡Qué día tan maravilloso!", "Me siento increíblemente feliz hoy", "Todo va perfectamente bien", "Estoy muy agradecido por todo", "Qué bien!, lograré ganar la materia")
val frasesNegativas = Array("Hoy es un día terrible", "Me siento muy triste", "Nada está yendo bien", "Estoy decepcionado y molesto", "Super aburrido", "Preocupado por la nota", "Perdere la materia, no puedo creerlo")
val frasesNeutras = Array("Hoy es un día normal", "No tengo mucho que decir", "Es un día más del montón", "Nada especial que reportar hoy", "Hoy no pasó nada interesante")
  val random = new Random

  def generateRandomTweet(): String = {
    val todasLasFrases = frasesPositivas ++ frasesNegativas ++ frasesNeutras
    val name = names(random.nextInt(names.length))
    val topic = topics(random.nextInt(topics.length))
    val fraseSentimiento = todasLasFrases(random.nextInt(todasLasFrases.length))
    s"@$name: Interesante artículo sobre $topic, Y mi estado para hoy es $fraseSentimiento . #${topic.replace(" ", "")}"
  }
  // Generador de hash
  def generateHash(tweet: String): Int = tweet.hashCode
  private def generateAndSendTweets(): Unit = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    
    val producer = new KafkaProducer[String, String](props)
    val random = new Random()
    
    try {
      while (!isStopped()) {
        println("Generando Tweet")
        val tweetText = generateRandomTweet()
        val tweet = s"User${random.nextInt(100)}: $tweetText"
        val hashKey = generateHash(tweet).toString
        val message = new ProducerRecord[String, String](topic, hashKey , tweet)
        producer.send(message)
        
        Thread.sleep(1000)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close()
    }
  }
}

// COMMAND ----------

val ssc = new StreamingContext(sc, Seconds(10))

val kafkaBootstrapServers = "34.123.51.250:9092"
val kafkaTopic = "TwitterKafka" // Reemplazar con tu topic de Kafka

val fakeTweetStream = ssc.receiverStream(new FakeTweetReceiver(kafkaBootstrapServers, kafkaTopic))
fakeTweetStream.foreachRDD { rdd =>
  if (!rdd.isEmpty()) {
    println("Enviando tweets falsos a Kafka...")
  }
}
ssc.start()
ssc.awaitTermination()

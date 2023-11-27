// Databricks notebook source
import org.apache.spark.sql.SparkSession

// Creación de la sesión de Spark
val spark = SparkSession.builder.appName("TwitterKafkaStream").getOrCreate()
import spark.implicits._

// Configuración de Spark para leer desde Kafka
val df = spark
  .readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", "34.123.51.250:9092")
  .option("subscribe", "TwitterKafka")
  .load()

// Procesamiento de los datos
val tweets = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)]

// Mostrar los resultados en consola
val query = tweets.writeStream
  .outputMode("append")
  .format("console")
  .start()

query.awaitTermination()

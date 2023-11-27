// Databricks notebook source
import org.apache.spark.sql.SparkSession

// Creación de la sesión de Spark
val spark = SparkSession.builder.appName("KafkaToS3Storage").getOrCreate()
spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", "AKIAUQGUET36CIVPGF4V")
spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", "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")
import spark.implicits._

// Lectura de datos desde Kafka
val df = spark
  .readStream
  .format("kafka")
  .option("kafka.bootstrap.servers", "34.123.51.250:9092")
  .option("subscribe", "TwitterKafka")
  .load()
  .selectExpr("CAST(value AS STRING)")

// Escritura en AWS S3
val query = df.writeStream
  .format("parquet")
  .option("path", "s3://hfbanilatqsparks3/dataspark")
  .option("checkpointLocation", "s3://hfbanilatqsparks3/checkpoint")
  .start()

query.awaitTermination()


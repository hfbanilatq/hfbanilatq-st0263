# Proyecto No 3.

| Información |  |
| --- | --- |
| Materia | Tópicos especiales en Telemática |
| Curso | ST0263 |
| Estudiantes | Hector Fabio Banilat Quintero (hfbanilatq@eafit.edu.co) |
|  | Daniel Solano Velasquez (dsolano@eafit.edu.co) |
| Profesor | Edwin Nelson Montoya Munera (emontoya@eafit.edu.co) |

# 1. Objetivo

---

El objetivo de este proyecto3 es implementar una arquitectura de Streaming de Big Data, que permita capturar datos en tiempo real de la famosa plataforma de Twitter, almacenarlos en un Data Lake y analizarlos en tiempo real; dichos datos deberán de ser llevados a un bucket de s3 y a un procesador de flujos que analice los datos conforme van llegando.

# 2. Video sustentación


# 3. Aspectos solucionados y no solucionados

---

- []  Captura de datos en tiempo real. (Se creo un faker de tweets debido a que el API de twetter no estaba funcionando)
- [x]  Subir los datos capturados a un Topic en Kafka.
- [x]  Almacenamiento de datos en un bucket de S3.
- [x]  Procesamiento de flujo de datos.
- [x]  Visualización en tiempo real.

# 4. Información general del diseño
Se empleó databricks para la creación del cluster de spark, además se empleó un VM en GCP donde se instaló kafka. Se emplea un bucket de S3 para el almacenamiento de los datos ingresados en la cola

## Sobre los servicios

En esta sección, se explican los servicios implementados para dar solución al reto propuesto, explicado en la sección: 1. Objetivo.

| Nombre del servicio | Rol que desempeña | IP y puertos de escucha |
| --- | --- | --- |
| spark-streaming-twitter_2.12| Libreria de spark par ala integración de la API  de tweeter|
| Apache Kafka | Almacena los datos crudos consultados por el servicio de TradeStreaming. | 34.123.51.250:9092 |
| DataBricks | Lee, procesa, transforma y carga a S3 los datos crudos, presentes en un tópico de Apache Kafka.  | - |
| S3 | Almacena los datos transformados por el servicio de DataBricks. | s3://hfbanilatqsparks3/dataspark |
| Grafana | Permite la visualización de los datos presentes en MongoDB.   | - |

# 5. Ambiente de desarrollo
Se emplea dos notebook en databricks, con el lenguaje scala 

## Estructura del código

.
├── databricks
│   └── FakerTweetConsumer.scala
│   └── FakerTweetConsumerWithStorage.scala
│   └── FakerTweetProducer.scala

## Creacion de cuenta en databricks y creacion de cluster
1. Ve a la consola de GCP y en el buscador por Databricks
2. En los resultados selecciona el de la marketplace
3. Dale clic en solicitar, llena los datos y espera que se te active
4. Inicia sesion en databricks
5. Ve a tus workspaces y crea un nuevo workspace
6. selecciona el workspace creado, ve al menu ->new-> cluster, crea el cluster y después importa el codigo de los archivos .scala en la carpeta databricks.

Nota: puedes seguir este tutorial: https://docs.gcp.databricks.com/getting-started/index.html

# Instalacion de kafka en una instancia de GCP

```bash
# Actualiza los paquetes existentes
sudo apt-get update

# Instala Java (Kafka requiere Java para funcionar)
sudo apt-get install default-jdk -y

# Descarga Kafka desde el sitio web oficial
wget https://archive.apache.org/dist/kafka/2.13-2.8.0/kafka_2.13-2.8.0.tgz

# Descomprime el archivo descargado
tar -xzf kafka_2.13-2.8.0.tgz

# Cambia al directorio de Kafka
cd kafka_2.13-2.8.0

# Inicia el servidor Zookeeper (Kafka usa Zookeeper para la gestión del cluster)
bin/zookeeper-server-start.sh config/zookeeper.properties &

# Inicia el servidor Kafka
bin/kafka-server-start.sh config/server.properties &
```
sigue este tutorial:
https://www.datasciencecentral.com/setting-up-your-first-kafka-development-environment-in-google/

## Configuración de parámetros del proyecto


# 6. Ambiente de ejecución

---

## Arquitectura general

En la presente sección se mostrara la arquitectura de referencia recomendada para el proyecto, la arquitectura final y los servicios de Google Cloud Platform (GCP) empleados para el despliegue eficiente y escalable.


## Guía de uso

La siguiente guía brindará los pasos a seguir para un correcto funcionamiento y ejecución del software.

### Instalando requisitos previos
Debemos instalar las librerias usando el UI de databricks en el cluster las librerias son:


edu.stanford.nlp:stanford-corenlp:4.5.5
Maven
-

org.apache.bahir:spark-streaming-twitter_2.12:2.4.0
Maven
-

org.apache.spark:spark-streaming-kafka-0-10-assembly_2.12:3.5.0

## Ejecución del programa

En la UI de databricks, crea un nuevo cuaderno de scala por cada archivo que hay en la carpeta databricks,importarlos al workspace y ejecutarlos en el orde, Producer, Consumer, ConsumerWithStorage.
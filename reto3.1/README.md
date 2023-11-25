# info de la materia: ST0263 TOPICOS ESPECIALES EN TELEMATICA
#
# Estudiante(s): Hector Fabio Banilat - hfbanilatq@eafit.edu.co
# Profesor: Edwin Monotya, emontoya@eafit.edu.co


# Reto 3.1
Tutorial: Manejo de HDFS en Amazon EMR

1. Conexión al Clúster de Amazon EMR:

Inicia una conexión segura utilizando SSH con tu llave de acceso:
````
ssh -i [ruta-de-tu-llave.pem] hadoop@[dirección-IP-del-master-node]
```
Ejemplo de conexión:
```
ssh -i bigdata-key-pair.pem hadoop@ec2-100-26-198-124.compute-1.amazonaws.com
```
2. Operaciones con Archivos en HDFS Mediante la Terminal:

Preparación de Datos:

Prepara un espacio en HDFS para tus datasets creando un directorio llamado 'datasets' dentro de tu directorio home (/user/hadoop).

Asegúrate de que tus datos ya estén descomprimidos y listos para su procesamiento.

Ejemplo de Datasets y Comandos Básicos:

Para obtener los datasets, clona el repositorio con el siguiente comando:
```
bash
git clone https://github.com/hfbanilatq/hfbanilatq-st0263
```
Navega al directorio de datasets:

```
bash
cd hfbanilatq-st0263/reto3.1/datasets
```
Lista los archivos presentes en el directorio raíz de HDFS:
```

``` bash

hdfs dfs -ls /
Inspecciona los contenidos de otros directorios relevantes con comandos similares:

``` bash

hdfs dfs -ls /user
hdfs dfs -ls /user/hadoop
hdfs dfs -ls /user/hadoop/datasets
```
Gestión de tu Directorio de Datasets:

Crea tu directorio 'datasets' en HDFS con:

``` bash

hdfs dfs -mkdir /user/hadoop/datasets
```
Transfiere tus archivos desde el servidor local al directorio HDFS que acabas de crear. Por ejemplo:

``` bash

hdfs dfs -put [ruta-local] /user/hadoop/datasets/
```
Si tus datos están en Amazon S3, puedes transferirlos directamente con:

``` bash

hadoop distcp s3://[ruta-en-s3] /destino-en-hdfs
```
Para una copia de datos recursiva utiliza:

``` bash

hdfs dfs -copyFromLocal [ruta-local] /user/hadoop/datasets/
```
Lista los archivos en tu directorio 'datasets' para verificar la transferencia:

``` bash

hdfs dfs -ls /user/hadoop/datasets
```
Recuperación de Archivos desde HDFS:

Para copiar archivos de vuelta al servidor local, usa:

``` bash

hdfs dfs -get /user/hadoop/datasets/* ~/[tu-username]/mis_datasets/
```
Otra opción para recuperar archivos es:

``` bash

hdfs dfs -copyToLocal /user/hadoop/datasets/[nombre-del-archivo] ~/[tu-username]/mis_datasets/
```
Verifica la transferencia con:

``` bash

ls -l ~/[tu-username]/mis_datasets
```
Experimenta con otros comandos:

Aplica diversos comandos de HDFS para gestionar tus archivos, como:

``` bash

hdfs dfs -du [ruta]                # Muestra el uso de disco
hdfs dfs -mv [origen] [destino]    # Mueve archivos
hdfs dfs -cp [origen] [destino]    # Copia archivos
hdfs dfs -rm [ruta]                # Elimina archivos
hdfs dfs -put [src-local] [dest-hdfs]  # Copia local a HDFS
hdfs dfs -cat [nombre-de-archivo]  # Muestra contenido de archivo
```
Además, gestiona los permisos y propiedad de los archivos con:

``` bash

hdfs dfs -chmod [-R] [modo] [archivo]
hdfs dfs -chown [username] [archivo]
hdfs dfs -chgrp [grupo] [archivo]
```
3. Administración de Archivos a través de HUE en Amazon EMR:

Inicia sesión en la interfaz de HUE para una gestión más visual de los archivos.

Navega por el sistema de archivos, crea directorios y sube archivos directamente desde la interfaz de HUE.

Visualiza el contenido de los archivos para confirmar su integridad.

El resto del contenido, incluyendo imagenes se encuentran en el documento TutorialManejoDeArchivosHDFS.pdf

#Referencias
Este tutorial fue creado siguiendo los pasos en la guia del profesor de la materia https://github.com/st0263eafit/st0263-232/blob/main/bigdata/01-hdfs/README.md
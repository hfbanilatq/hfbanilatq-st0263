# info de la materia: ST0263 TOPICOS ESPECIALES EN TELEMATICA
#
# Estudiante(s): Hector Fabio Banilat - hfbanilatq@eafit.edu.co, Daniel Alfonso Solano - dsolano@eafit.edu.co, David Sanchez - dasancheza@eafit.edu.co
#
# Profesor: Edwin Monotya, emontoya@eafit.edu.co


# Reto 3

 Se debe implementar una aplicacion monolitica como drupal o wordpress, usando 5 instancias de EC2 que se definirán acontinuación:

     1. Instancia de EC2 donde se debe ejecutar un contenedor de docker con nginx que funcionara como balanceador de carga

     2. Dos intancias de EC2 que deberan correr la aplicación en Docker en la cual seleccionamos Drupal

     3. Instancia de MYSQL que se ejecutará en un contenedor de Docker

     4. Una intancia de un servidor NFS, El cual también correrá en un contenedor docker

<texto descriptivo>

## 1.1. Que aspectos cumplió o desarrolló de la actividad propuesta por el profesor (requerimientos funcionales y no funcionales)

Se desplegó el servidor de Mysql usando docker-compose, también se configuró una instancia de EC2 Linux 2 AMI como servidor de arcvhivos NFS, se desplegaron dos servidores de EC2 donde se ejecutan las instancias de DRUPAL 9 usando docker-compose, se desplegó el sevidor nginx que sirve como balanceador de carga para las dos instancias de drupal. En los archivos de docker-compose se crean un volumen que monta la carpeta /var/www/html del servidor nfs donde se guardarán los archivos

Los servidores se les asignó una ip elastica de la siguiente manera:


    1. SERVIDOR NFS: 54.234.20.128
    2. SERVIDOR MYSQL: 34.196.121.51
    3. DRUPAL INSTANCIA 1: 44.218.130.159
    4. DRUPAL INSTANCIA 2: 44.216.81.68
    5. NGINX LOAD BALANCER: 34.201.89.10


## 1.2. Que aspectos NO cumplió o desarrolló de la actividad propuesta por el profesor (requerimientos funcionales y no funcionales)

Por indicacion del profesor no se continuo con la adquisición del dominio y su posterior instalación de certificados

# 2. información general de diseño de alto nivel, arquitectura, patrones, mejores prácticas utilizadas.
Como se explico en la definicion de la actividad se tiene un servidor que corre un contenedor en docker que sirve como balanceador de carga para dos servidores que están corriendo imagenes de drupal, además se tiene un servidor sql donde ambos drupal obtienen los datos y por ultimo se tiene un servidor nfs donde los archivos de drupal se almacenan para que sin importar el drupal que el load balancer me redireccione la solicitud tengan los mismos archivos

# 3. Descripción del ambiente de desarrollo y técnico: lenguaje de programación, librerias, paquetes, etc, con sus numeros de versiones.
Se están empleando imagenes de gninx, mysql, y drupal las cuales se están ejecutando mediante archivos docker-compose. Se emplead docker-compose para subir las aplicaciones

## Comandos Para instalar docker y activar docker-compose e instalar git en AWS EC2 Estos comandos son para instancias Amazon Linux 2 AMI 
# INSTALAR GIT:
 sudo yum install -y git
# CLONAR el repositorio: 
git clone https://github.com/hfbanilatq/hfbanilatq-st0263.git
#  Instalar el servidor nfs
NOTA IMPORTANTE PARA PODER CREAR EL NFS SERVER NECESITAS EJECUTAR LOS COMANDOS EN EL DOCUMENTO 

crear-directorios-nfs.sh  

/nfs/ EN EL SERVIDOR NFS y luego corre lo que se encuentra en el documento. ingnorar el documento  nfs-instalar-y-montar-sh 

ya este se ejecuta en ambientes donde el drupal se ejecuta localmente el archivo docker-compose.yml para drupal ya tiene configurado montar un volumen nfs al arrancar

# NOTA ESTOS COMANDOS ESTÁN EN EL ARCHIVO instalar-docker-compose.sh 
El cual te permite ejecutar todos a la vez para esto vete a la carpeta hfbanilatq-st063/reto3 y ejecuta lo siguiente: 

<code>
chmod +x instalar-docker-compose.sh 
sudo ./instalar-docker-compose.sh
</code>


# Comandos para instalar docker
<code>

sudo amazon-linux-extras install docker

sudo service docker start

sudo usermod -a -G docker ec2-user

sudo chkconfig docker on
</code>

# REINICIAR LA INSTANCIA: 


<code
    sudo reboot
</code>

<code>
    sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

    sudo chmod +x /usr/local/bin/docker-compose

</code>
# COMPOBAR LA INSTALACION DE DOCKER-COMPOSE: 

<code>
   docker-compose version
</code>

# Deplegar las aplicaciones en docker
moverse la carpeta del proyecto git luego a reto3 y por ultimo a la carpeta del sevicio que quieres ejecutar: cd hfbanilatq-st0263/reto3/mysql/{CARPETA_OBJETIVO}

# MONTAR EL SERVICIO EN DOCKER COMPOSE: 
<code>
  docker-compose up -d
</code>

# VERIFICAR QUE SE EJECUTE CORRECTAMENTE: 
<code>
docker-compose ps
</code>

## detalles del desarrollo
Se empelaron archivos docker-compose, por lo tanto se requiere instalar docker-compose en los servidores, se emplearon 5 instnacias de EC2 que corren sobre la misma VPC, la cual tiene 4 subnet, 2 publicas y 2 privadas, aunque al montar las instancias se utilizaron las publicas para realizar la actividad en todos los servidores, se debe crear las instancias para el nfs-server, los drupal y mysql en subnets privadas. Y acceder directamente a través de la IP privada desde el servidor nginx

## Parametros del proyecto
Los parametros de configuracion para los docker-compose .yml se deben configurar en un archivo .env dentro del 
mismo directorio
# Ejemplo .env Para mysql
<code>
MYSQL_ROOT_PASSWORD=
MYSQL_DATABASE=
MYSQL_PASSWORD=
TZ=America/Bogota
</code>

# Ejemplo .env Para drupal

MYSQL_HOST:

MYSQL_ROOT_PASSWORD=

MYSQL_DATABASE=

MYSQL_PASSWORD=

TZ=America/Bogota


## 
## Resultados 

# 4. Descripción del ambiente de EJECUCIÓN (en producción) lenguaje de programación, librerias, paquetes, etc, con sus numeros de versiones.

# IP o nombres de dominio en nube o en la máquina servidor.
    1. SERVIDOR NFS: 54.234.20.128
    2. SERVIDOR MYSQL: 34.196.121.51
    3. DRUPAL INSTANCIA 1: 44.218.130.159
    4. DRUPAL INSTANCIA 2: 44.216.81.68
    5. NGINX LOAD BALANCER: 34.201.89.10

## descripción y como se configura los parámetros del proyecto (ej: ip, puertos, conexión a bases de datos, variables de ambiente, parámetros, etc)

# 5. otra información que considere relevante para esta actividad.

Se generaron varios inconvenientes al momento de montar los servidores drupal la carpeta en el servidor nfs, ya que no tenia acceso, se intentó montar directamente en una carpeta del sistema y luego pasarle esa carpeta como volumen de docker pero seguía generando problemas, hasta que se hayó la solución que es darle el control de /var/www/html al usuario ec2-user de esta forma al montar el volumen desde docker-compsose ya no generaba problemas de acceso y tampoco generaba problemas de lectura

# referencias:
<debemos siempre reconocer los créditos de partes del código que reutilizaremos, así como referencias a youtube, o referencias bibliográficas utilizadas para desarrollar el proyecto o la actividad>
## https://www.digitalocean.com/community/tutorials/how-to-install-drupal-with-docker-compose
## https://forums.docker.com/t/docker-compose-mount-volume-nfs/132551
## https://www.digitalocean.com/community/tutorials/how-to-set-up-an-nfs-server-using-block-storage
## https://bluexp.netapp.com/blog/azure-anf-blg-linux-nfs-server-how-to-set-up-server-and-client
## https://docs.aws.amazon.com/es_es/efs/latest/ug/mounting-fs-old.html

## url de donde tomo info para desarrollar este proyecto

#### versión README.md -> 1.0 (2022-agosto)

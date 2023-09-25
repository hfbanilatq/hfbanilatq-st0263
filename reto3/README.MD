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
#
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
NOTA IMPORTANTE PARA PODER CREAR EL NFS SERVER NECESITAS EJECUTAR LOS COMANDOS EN EL DOCUMENTO /nfs/ EN EL SERVIDOR NFS y luego corre lo que se encuentra en el documento crear-directorios-nfs.sh 
Ingnorar el documento nfs-instalar-y-montar-sh este se ejecuta en ambientes donde el drupal se ejecuta localmente el archivo docker-compose.yml para drupal ya tiene configurado montar un volumen nfs al arrancar

# NOTA ESTOS COMANDOS ESTÁN EN EL ARCHIVO instalar-docker-compose.sh 
El cual te permite ejecutar todos a la vez para esto vete a la carpeta hfbanilatq-st063/reto3 y ejecuta lo siguiente: 
chmod +x instalar-docker-compose.sh 
sudo ./instalar-docker-compose.sh

# Comandos para instalar docker
sudo amazon-linux-extras install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
sudo chkconfig docker on


# REINICIAR LA INSTANCIA: 
sudo reboot

 sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose
# COMPOBAR LA INSTALACION DE DOCKER-COMPOSE: 
docker-compose version

# Deplegar las aplicaciones en docker
moverse la carpeta del proyecto git luego a reto3 y por ultimo a la carpeta del sevicio que quieres ejecutar: cd hfbanilatq-st0263/reto3/mysql/{CARPETA_OBJETIVO}

# MONTAR EL SERVICIO EN DOCKER COMPOSE: 
docker-compose up -d
# VERIFICAR QUE SE EJECUTE CORRECTAMENTE: 
docker-compose ps






#
## detalles del desarrollo.
## detalles técnicos
## Parametros del proyecto
Los parametros de configuracion para los docker-compose .yml se deben configurar en un archivo .env dentro del 
mismo directorio
# Ejemplo .env Para mysql
MYSQL_ROOT_PASSWORD=admin
MYSQL_DATABASE=drupal
MYSQL_PASSWORD=admin
TZ=America/Bogota
## opcional - detalles de la organización del código por carpetas o descripción de algún archivo. (ESTRUCTURA DE DIRECTORIOS Y ARCHIVOS IMPORTANTE DEL PROYECTO, comando 'tree' de linux)
## 
## opcionalmente - si quiere mostrar resultados o pantallazos 

# 4. Descripción del ambiente de EJECUCIÓN (en producción) lenguaje de programación, librerias, paquetes, etc, con sus numeros de versiones.

# IP o nombres de dominio en nube o en la máquina servidor.

## descripción y como se configura los parámetros del proyecto (ej: ip, puertos, conexión a bases de datos, variables de ambiente, parámetros, etc)

## como se lanza el servidor.

## una mini guia de como un usuario utilizaría el software o la aplicación

## opcionalmente - si quiere mostrar resultados o pantallazos 

# 5. otra información que considere relevante para esta actividad.

# referencias:
<debemos siempre reconocer los créditos de partes del código que reutilizaremos, así como referencias a youtube, o referencias bibliográficas utilizadas para desarrollar el proyecto o la actividad>
## sitio1-url 
## sitio2-url
## url de donde tomo info para desarrollar este proyecto

#### versión README.md -> 1.0 (2022-agosto)
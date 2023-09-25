# info de la materia: ST0263 TOPICOS ESPECIALES EN TELEMATICA
#
# Estudiante(s): Hector Fabio Banilat - hfbanilatq@eafit.edu.co, Daniel Alfonso Solano - dsolano@eafit.edu.co
#
# Profesor: Edwin Monotya, emontoya@eafit.edu.co


# Reto 3
#
# 1. Se debe implementar una aplicacion monolitica como drupal o wordpress, usando 5 instancias de EC2 que se definirán acontinuación
# 1.1 Instancia de EC2 donde se debe ejecutar un contenedor de docker con nginx que funcionara como balanceador de carga
# 1.2 Dos intancias de EC2 que deberan correr la aplicación en Docker en la cual seleccionamos Drupal
# 1.3 Instancia de MYSQL que se ejecutará en un contenedor de Docker
# 1.4 Una intancia de un servidor NFS, El cual también correrá en un contenedor docker
#
<texto descriptivo>
## 1.1. Que aspectos cumplió o desarrolló de la actividad propuesta por el profesor (requerimientos funcionales y no funcionales)

## 1.2. Que aspectos NO cumplió o desarrolló de la actividad propuesta por el profesor (requerimientos funcionales y no funcionales)

# 2. información general de diseño de alto nivel, arquitectura, patrones, mejores prácticas utilizadas.

# 3. Descripción del ambiente de desarrollo y técnico: lenguaje de programación, librerias, paquetes, etc, con sus numeros de versiones.

## Comandos Para instalar docker y activar docker-compose e instalar git en AWS EC2 Estos comandos son para instancias Amazon Linux 2 AMI 
# INSTALAR GIT: sudo yum install -y git
# CLONAR el repositorio: git clone https://github.com/hfbanilatq/hfbanilatq-st0263.git

# NOTA ESTOS COMANDOS ESTÁN EN EL ARCHIVO instalar-docker-compose.sh que te permite ejecutar todos a la vez
# sudo amazon-linux-extras install docker
# sudo service docker start
# sudo usermod -a -G docker ec2-user
# sudo chkconfig docker on


# REINICIAR LA INSTANCIA: sudo reboot

# sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose

# sudo chmod +x /usr/local/bin/docker-compose
# COMPOBAR LA INSTALACION DE DOCKER-COMPOSE: docker-compose version

# MOVERSE A LA CARPETA DEL SERVICIO QUE QUIERAS DESPLEGAR EN DOCKER: cd hfbanilatq-st0263/reto3/mysql/{CARPETA_OBJETIVO}

# MONTAR EL SERVICIO EN DOCKER COMPOSE: docker-compose up -d
# VERIFICAR QUE SE EJECUTE CORRECTAMENTE: docker-compose ps
#
## detalles del desarrollo.
## detalles técnicos
## descripción y como se configura los parámetros del proyecto (ej: ip, puertos, conexión a bases de datos, variables de ambiente, parámetros, etc)
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
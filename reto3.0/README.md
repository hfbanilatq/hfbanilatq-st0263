# info de la materia: ST0263 TOPICOS ESPECIALES EN TELEMATICA
#
# Estudiante(s): Hector Fabio Banilat - hfbanilatq@eafit.edu.co
# Profesor: Edwin Monotya, emontoya@eafit.edu.co

# NOTA: La guia completa con imagenes se encuentra en el archivo reto3.0\totuorial de creacion cluster emr.pdf


Universidad Eafit
Laboratorio 3.0:  Instalación de un cluster EMR en AWS
Para desarrollar este laboratorio necesitamos acceso a una cuenta de AWS, debemos iniciar sesión y una vez allí necesitaremos crear:
1.	Un bucket de s3 para almacenar la información del cluster, logs y demás.
2.	Un cluster EMR
3.	Configurar el grupo de seguridad para habilitar los puertos necesarios de las aplicaciones a internet
4.	Configurar el nodo maestro del cluster para solucionar un error de puertos.
Para crear un bucket de s3 debemos poner en el buscador “S3” y seleccionar e servicio de S3
 
Una vez seleccionado el servicio de S3 debemos dar click en crear bucket

 
El paso anterior nos enviará a la página de creación del bucket donde debes darle un nombre, Seleccionar la región y la propiedad del bucket como sigue  
Una vez configurada esta información darle clic en crear bucket
 
Esto debería crear el bucket de S3 y nos permitirá continuar al siguiente paso

Crear un cluster de EMR: Para realizar este paso necesitaremos ir al panel de control del servicio EMR para ello buscaremos EMR y seleccionaremos el servicio
 
En este panel veremos un botón que nos enviará a la página de creación del cluster damos clic allí
 
En esta página asignamos un nombre al clúster y seleccionamos las aplicaciones que se ven en la imagen 
La siguiente información debes configurarla igual, el tamaño mínimo sugerido para las maquinas de EC2 son m5.xlarge
 
 

En mi caso seleccione un escalado administrado por AWS pero puedes dejarlo por defecto
 
Se selecciona la VPC y la subred, si no tienes VPC actualmente puedes crear una nueva al dar clic en Crear PVC, de igual forma para la subnet
 
Puedes asignar un grupo de seguridad que ya tengas, como es mi caso o en su defecto usar el que crea actomaticamente AWS
 
Ahora vamos a relacionar el bucket de S3 al cluster, para ello damos clic en examinar y seleccionamos el bucket de S3 que creamos


  
Ahora debemos agregar configuración del software, para ello agregamos lo siguiente en el campo de texto: 
[
  {
    "Classification": "jupyter-s3-conf",
    "Properties": {
      "s3.persistence.bucket": "hfbanilats3",
      "s3.persistence.enabled": "true"
    }
  }
]
En mi caso la propiedad       "s3.persistence.bucket" es el nombre del bucket de s3 que creé, para tu caso debes poner el que hayas creado
 
En los pares de claves debes seleccionar un par de claves si tienes, en caso de que no debes dar clic en crear par de claves, asignar un nombre y luego seleccionar ese par de claves
 
  
Debe seleccionar: 
 Service role: EMR_DefaultRole 
Instance profile: EMR_EC2_DefaultRole 
Custom automatic scaling role: LabRole
  
Y ya simplemente dar clic en crear cluster
Una vez creado el cluster debes esperar que esté en estado “Esperando”, puede dardar hasta 20 min en completarse
 
Ahora vamos a abrir los puertos necesarios en el grupo de seguridad, para ello debes primero en el panel de control de EMR en el menú izquierdo seleccionar “Bloquear acceso publico” en este lado debes desactivarlo y luego dirigirte al panel de EC2.

En el panel de EC2 busca la instancia en ejecución cuyo grupo de seguridad tenga la palabra master
 

Dale clic en el y dirigete a la parte de seguridad:  
Selecciona el grupo de seguridad y dale clic, allí iremos al grupo de seguridad y debemos crear las siguientes reglas de entrada:    

Con esto las aplicaciones podrán comunicarse entre sí. 
Ahora nos dirigimos al cluster nuevamente en en la parte de aplicaciones damos clic en Tonalidad o hue
 
Asignamos el usuario hadoop y la contraseña de nuestra preferencia
 
Ahora vamos a ver si nos genera un error en los archivos, para ello en el menú damos clic en Files, si nos sale el error como viene:  
Ahora como ultimo paso debemos configurar el nodo maestro para que se elimine el error que nos para ello accedemos con el par de claves 
   

editamos el archivo hue.ini  “sudo nano /etc/hue/conf/hue.ini”  buscar la línea que contenga: ‘webhdfs-url’ y cambiar el puerto de 14000 a 9870  (en nano puede utilizar control-w para buscar la palabra)  
Por ultimo reiniciamos el servicio 
“sudo systemctl restart hue.service”

Ahora vamos a ingresar a Jupyter-hub para ello volvemos a cluster en aplicaciones y damos clic en Jupyter-hub  
Y una vez alli usaremos las credenciales por defecto:
Username: jovyan 
Password: jupyter
 
 
Ahora vamos a crear un notebook, para ello damos clic en new, y seleccionamos PySpark
 

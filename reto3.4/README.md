# info de la materia: ST0263 TOPICOS ESPECIALES EN TELEMATICA
#
# Estudiante(s): Hector Fabio Banilat - hfbanilatq@eafit.edu.co
# Profesor: Edwin Monotya, emontoya@eafit.edu.co


# Lab 3.4: Laboratorio Spark con databricks
Objetivo: Ejecutar cargas de trabajo para analisis de datos usando un cluster de Spark y Notebooks con databricks




Para este laboratorio se creará un cluster de spark con databricks, para ello necesitas una cuenta de databricks puedes solicitar una con GCP ve a la consola de GCP y en el buscador pon databricks, allí se debe realizar la solicitud y te enviará a la página de registro. Una vez aprobada en el resultado de búsqueda de gcp te debe aparecer algo como esto:
 

Al dar clic en “ADMINISTRAR EN EL SITIO DEL PROVEEDOR” te saldrá una alerta de privacidad y al darle aceptar te abrirá una nueva ventana con databricks, inicia sesión con el correo que te registraste y te saldrá algo así :  
Una vez acá necesitamos crear un workspace donde vamos a trabajar en el lab para ello seleccionamos workspaces y damos a crear workspace
 

Asignamos un nombre al workspace, seleccionamos una zona de disponibilidad y ponemos el id del proyecto con el que vamos a trabajar 
 
Para saber el ID del proyecto ve a la consola de GCP y en donde se selecciona el proyecto dale en administrar y te saldrán los proyectos, allí verás el ID:
 

Creamos el workspace, si no aparece de inmediato cierra sesión e inicia de nuevo. Ahora seleccionamos el workspace
 
Una vez termine de aprovisionar damos clic en el nombre del workspace y damos clic en Open Workspace, esto nos abrirá una nueva ventana con la que podremos crear Notebooks y clusters para trabajar, en nuestro caso vamos a crear un cluster personal para trabajar con Spark. Para ello damos clic en nuw y seleccionamos cluster
 
Seleccionamos personal Compute, y nuestro usuario y damos clic en crear, también se puede cambiar la versión de spark y scala, en mi caso lo dejé por defecto. 
 

Esto tardará un tiempo, una vez creado podremos crear notebooks y ejecutarlos usando el cluster que creamos. Mientras eso pasa, vamos a crear un bucket con cloud storage donde vamos a subir los archivos que vamos a emplear en el análisis
Para ello vamos a la consola de GCP y buscamos cloud storage, seleccionamos la opción , allí damos clic en crear
 
Asiganmos un nombre y damos clic en continuar
 

El siguiente paso seleccionamos regional y seleccionamos una región 
 
Deseleccionamos el “Aplicar prevención de acceso público al bucket” en la pestaña de “Elegir como controlar el acceso a los objetos”
 
Y listo, lo demás se queda tal cual está y damos clic en crear. Nos quedará algo así:
 
Ahora vamos a subir los datos al bucket para ello necesitas descargar el dataset en el repo, en la carpeta reto3.1 se encuentra.

Creamos la carpeta covid-19 y subimos los archivos a esta carpeta, que se encuentran en la carpeta covid dentro de datasets. Subimos ambos archivos
 

Una vez hecho esto, ya podemos proceder a crear el Notebook para analizar los datos, databricks tiene las variables sc y pyspark por defecto, por lo que no será necesario crearlas. Vamos a crear el Notebooks, para ello nos dirigimos al workspace de Databricks y damos en new -> Notebook (Cuaderno si está en español como el mío)

 

Esto nos abrirá un cuaderno en blanco, podemos programar en Scala, Python, Sql o R, para propósitos del laboratorio vamos a trabajar en Python, ya que en el repo del profesor https://github.com/st0263eafit/st0263-232/tree/main/bigdata/02-spark se encuentra una guía sobre cómo trabajar con los datos. Si gustas puedes primero intentar trabajar con los códigos que indica el profe para que entiendas un mejor este trabajo.

Ahora procedemos a crear el código de Python, primero obtendremos los datos de nuestro bucket. Para ello necesitamos configurar databricks para que pueda acceder al bucket, debemos seguir este tutorial para darle acceso al bucket a databricks  
https://docs.databricks.com/en/storage/gcs.html

Acá te doy un resumen, debemos ir a la consola de GCP, y buscar IAM and Admin, Ahí vamos a Cuentas de servicio y creamos una nueva para darle solo acceso al bucket
 
Damos el nombre la descripción y creamos. D
Una vez creada la debemos seleccionar la cuenta recien creada (DEBEMOS COPIAR EL CORREO QUE APARECE LO USAREMOS MAS ADELANTE), y damos clic en CLAVES, y damos crear nueva clave, seleccionamos json y listo
 

Ahora vamos al bucket, lo seleccionamos y damos clic en permisos. Y allí OTORGAR ACCESO. En esta parte copiamos el correo que se generó en el paso de creación de la cuenta de servicio, luego adicionar Role -> Cloud Storage -> Administrador de almacenamiento
 
Ahora vamos a configurar el cluster con este correo de cuenta de acceso, para ello vamos al menú compute  ahí seleccionamos nuestro cluster y luego damos en editar
 
 
En opciones avanzadas pegamos la cuenta de servicio que creamos con el acceso Y ya deberíamos tener acceso al bucket.

Ya podemos empezar con el desarrollo, para ello vamos al notebook que creamos anteriormente y pegamos cada celda que está en el archivo Analisis_Covid.ipynb (También podemos importarlo)

Y damos ejecutar todas las celdas y listo, ya tenemos nuestro análisis, acá te voy a dejar una captura de pantalla de cada celda y su resultado:
 
 
 
 
 
 
     
 
   

El Siguiente comando guarda los datos en el bucket de GCP
 
 
Y un extra:
 

Y listo, con esto finalizamos el Laboratorio.

# URL BUCKET
gs://lab-sparkhfbanilatq/covid-19/







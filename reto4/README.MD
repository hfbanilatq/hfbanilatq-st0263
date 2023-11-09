# info de la materia: ST0263 TOPICOS ESPECIALES EN TELEMATICA
#
# Estudiante(s): Hector Fabio Banilat - hfbanilatq@eafit.edu.co, Daniel Alfonso Solano - dsolano@eafit.edu.co
#
# Profesor: Edwin Monotya, emontoya@eafit.edu.co


# Reto 4

Se debe implementar una aplicacion monolitica como drupal o wordpress, en kubernetes con alta disponibilidad en la base de datos, alta disponibilidad en la cada de aplicacion y en la capa de almacenamiento

Se desplegaron 3 VM en gcp con sistema operativo Ubuntu las maquinas tienen un disco principal de 10 gb y un disco secundario de 100gb el disco secundario es obligatorio ya que se empleará Rook/Ceph instalado en el cluster para la alta disponibilidad en la capa de datos

Se empleará además mysql-operator para crear un cluster de base de datos en alta disponibilidad

Para el despliegue de la aplicación wordpress se empleará un deployment con alta disponibilidad


## 1.1. Que aspectos cumplió o desarrolló de la actividad propuesta por el profesor (requerimientos funcionales y no funcionales)
Se cumplieron con todos los aspectos, aunque se dificulto bastante el tema de la alta disponibilidad en la capa de datos

## 1.2. Que aspectos NO cumplió o desarrolló de la actividad propuesta por el profesor (requerimientos funcionales y no funcionales)
# 2. información general de diseño de alto nivel, arquitectura, patrones, mejores prácticas utilizadas.
Cluster kubernetes con 3 nodos en HA, mysql operator corriendo en el cluster, mysql innodb cluster, drupal deployment, serice y pvc con nfs y hpa para el escalamiento automatico.

# INSTALAR GIT:
 sudo apt install -y git
# CLONAR el repositorio: 
```bash
git clone https://github.com/hfbanilatq/hfbanilatq-st0263.git
```
#  Instalar Microk8s en cada servidor

Ejecutar:  
```bash
        sudo apt update
        sudo snap install microk8s --classic
        sudo usermod -a -G microk8s hfbanilatq
        sudo microk8s enable cert-manager dashboard hostpath-storage ingress metrics-server
```        

Desde el nodo principal ejecutar:

```bash
        microk8s add-node
```
copiar lo que sale y ejecutarlo en los nodos workers, se debe ejecutar una vez por worker el mismo comando para que le genere un comando de conexion al cluster, para usar kubectl directamente ejecuta este comando:

```bash
    alias kubectl="microk8s kubectl" 
```
# Instalar OpenEBS en el cluster

```bash
    sudo systemctl enable --now iscsid
    microk8s helm repo add openebs-cstor https://openebs.github.io/cstor-operators
    microk8s helm repo update
    microk8s helm install openebs-cstor openebs-cstor/cstor -n openebs --create-namespace --set-string csiNode.kubeletDir="/var/snap/microk8s/common/var/lib/kubelet/"
    kubectl get pod -n openebs
    kubectl get bd -n openebs
 ```


En caso de que por algún motivo no funcione también puedes seguir el tutorial para usar openebs  [cStor](https://github.com/openebs/cstor-operators/blob/develop/docs/quick.md) en la carpeta openebs/cStor hay un ejemplo de la implementacion para 3 storage. Se debe modificar los valores como indica el tutorial para que coincida con los que se encuentran en tu cluster

```bash
    kubectl apply -f openebs/cStor/cspc-multi.yaml
    kubectl apply -f openebs/cStor/storageclass.yaml
 ```

# Habilitar NFS en el cluster 
Como se deben conectar varios pods a los persistence volumes es necesario que los PVC creados tengan la capacidad de ser leidos y escritos multiples veces para ello se empleará la capacidad que está en estado beta de OpenEBS se debe seguir el siguiente tutorial [NFS Dynamic Provisioner OpenEBS](https://github.com/openebs/dynamic-nfs-provisioner/blob/develop/docs/intro.md)


Acá dejaré un resumen de los comandos a usar:

   ```bash
    sudo apt install nfs-common -y
    microk8s helm repo add openebs https://openebs.github.io/charts
    microk8s helm repo update
    microk8s helm install openebs openebs/openebs -n openebs --create-namespace   --set ndm.enabled=false --set localprovisioner.enabled=false --set nfs-provisioner.enabled=true
    
    kubectl apply -f openebs/cStor/nfs-storageclass.yaml 
```

# Instalar la base de datos en alta disponibilidad en Kubernetes

Para instalar la base de datos en alta disponibilidad se requiere tener un aprovisonador de almanamiento "StorageClass" para que genere automaticamente los pvc y pv de la base de datos para ello emplearemos rook/ceph en el cluster

Se instalará empleando helm3 para ello necesitas tenerlo instalado en tu PC

```bash
helm repo add rook-release https://charts.rook.io/release
helm install --create-namespace --namespace rook-ceph rook-ceph rook-release/rook-ceph -f https://github.com/rook/rook/blob/master/deploy/charts/rook-ceph/values.yaml
```

Para la base de datos en alta disponibilidad se empleará mysql-operator que es una solución realizada por mysql, para lo cual se deben ejecutar los siguientes comandos:

```bash
    kubectl apply -f https://raw.githubusercontent.com/mysql/mysql-operator/trunk/deploy/deploy-crds.yaml
    kubectl apply -f https://raw.githubusercontent.com/mysql/mysql-operator/trunk/deploy/deploy-operator.yaml
```

para validar que todo se haya ejecutado correctamente se debe emplear el siguiente comando:

```bash
    kubectl get deployment mysql-operator --namespace mysql-operator
```

si los procesos resultantes estan en estado running, el operador de mysql se instaló correctamente, ahora vamos a ejecutar los archivos que se encuentran dentro de la carpeta mysql

```bash
    kubectl apply -f mysql/secrets.yaml
    kubectl apply -f mysql/mysql-cluster.yaml
```

con esto queda habilitada la base de datos en alta disponibilidad. 

# Instalación de drupal usando kubectl
Antes que nada para ejecutar estos comando primero se ejecutó alias kubectl="microk8s kubectl"

Ahora bien, para lograr la instalación de drupal en un cluster de kubernetes se deben ejecutar los archivos dentro de la carpeta kubernetes/drupal

```bash
    kubectl apply -f drupal/pvc.yaml
    kubectl apply -f drupal/deployment.yaml
    kubectl apply -f drupal/ingress.yaml 
```

Ese deployment solo tiene 1 replica de drupal, ya que si se inicia con más de una replica genera conflictos al inicializar el pods, ya que ambos intentan instalarse al mismo tiempo, generando varios reinicios, por eso se dejó el deployment a una sola replica, para cumplir con la alta disponibilidad en la capa de aplicacion se empleará un HPA (Escalamiento horizontal automatico de pods) para ello se debe esperar que el deploymente esté correctamente ejecutado y que tenga el pod en estado ready sin ningun reinicio. Una vez se cumple ejecuta:

```bash
    kubectl apply -f drupal/drupal-hpa.yaml
```

Si no tienes certificado y quieres que el ingress lo genere automaticamente debes ejecutar lo siguiente:

```bash
    kubectl delete -f drupal/ingress.yaml 
    kubectl apply -f drupal/cert-staging.yaml
    kubectl apply -f drupal/ingress-ssl.yaml
```
Si por alguna razón te llega a fallar la instalación por algún problema de base de datos prueba mejor realizar la instalación usando helm charts para ellos ejecuta:

```bash
    microk8s helm install drupal bitnami/drupal -f drupal/helm/values.yaml
```
al igual que en la instalacion manual se cuenta con una sola replica, para ejecutar mas replias debes descomentar la linea replicaCounts y adicionar el valor de replicar que quieras luego debes ejecutar un upgrade asi:


```bash
    microk8s helm upgrade drupal -f drupal/helm/values.yaml
```

y listo ya estaría todo correcto.


con lo anterior ya queda totalmente configurada la app de drupal ahora solo flata que dirijas el trafico de tu dominio al cluster en mi caso emplee un balanceador de carga de google para balancear el grupo de instancias.

## detalles del desarrollo
Para este reto se desplegaron 3 VM de Ubuntu gcp  con un disco principal de 40 gb y uno secundario de 70 GB, en cada uno de las VM se instaló microk8s y se uso el comando microk8s add-node para añadir dos instancias como worker a la instancia principal. Se emplearon soluciones implementadas para kubernetes tales como OpenEBS, MysqlOperator, Ingress, Metallb. La aplicacion drupal corre en un deployment con dos replicas iniciales y tiene un HorizontalPodAutoscaler para el escalamiento automatico


## Parametros del proyecto
Toda la configuracion está descrita previamente, si es necesario ver los tutoriales que se describen mejor como funciona cada cosa
# Ejemplo .env Para mysql
Se tiene varias carpetas, cada una con las configuraciones custom para instalar cierta solucion, drupal para poner a correr la app de drupal en alta disponibilidad, mysql para poder desplegar un cluster de mysql, openebs para poder usar almacenamiento en alta disponibilidad.

## 
# Resultados 
Se logra el despligue y la activacion del dominio (Al ya no existir dominions .tk gratuitos, no encontramos donde) se compró un dominio por 2 dls por un año, igualmente el certificado se consiguio con ZEROSSL es un certificado de 3 meses, hubo varias inconvenientes para poder crear un sistema de almacenamiento altamente disponible debido a las limitaciones que tiene microk8s, se intentó con un NFS en el sistema operativo directamente, se intentó con glusterfs, de igual forma con Rook/Ceph y en todos surgía un error distinto la mayoría de veces por los requisitos o permisos que pedían y que microk8s no ofrece. Al final se logró hallar una solución con OpenEBS usando cStor y NFS.
# 4. Descripción del ambiente de EJECUCIÓN (en producción) lenguaje de programación, librerias, paquetes, etc, con sus numeros de versiones.

# IP o nombres de dominio en nube o en la máquina servidor.
    1. 
    2.
    3.
    4. GRUPO DE INSTANCIAS: drupal-hpa

## descripción y como se configura los parámetros del proyecto (ej: ip, puertos, conexión a bases de datos, variables de ambiente, parámetros, etc)
Para la conexion a la base de datos se debe usar el servicio mysql-cluster y la contraseña admin

# 5. otra información que considere relevante para esta actividad.

Se generaron varios inconvenientes al momento de montar los servidores drupal la carpeta en el servidor nfs, ya que no tenia acceso, se intentó montar directamente en una carpeta del sistema y luego pasarle esa carpeta como volumen al kubernetes pero seguía generando problemas, en general con el reto 3 y 4 nos hems dado cuenta que la mayor complejidad es establecer una alta disponibilidad de los datos en DFS, ya que se requiere un configuración y conocimientos extensos para realizar esta tarea titanica.

# referencias:
<debemos siempre reconocer los créditos de partes del código que reutilizaremos, así como referencias a youtube, o referencias bibliográficas utilizadas para desarrollar el proyecto o la actividad>
https://github.com/openebs/cstor-operators/blob/develop/docs/quick.md
https://dev.mysql.com/doc/mysql-operator/en/
https://blogit.michelin.io/statefull-application-on-kubernetes/
https://openebs.io/docs/concepts/casengines
https://github.com/openebs/dynamic-nfs-provisioner/blob/develop/docs/intro.md

## url de donde tomo info para desarrollar este proyecto

#### versión README.md -> 1.0 (2022-agosto)

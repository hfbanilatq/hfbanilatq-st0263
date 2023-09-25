sudo mkdir -p /nfsshared
sudo mount -t nfs -o nfsvers=4.1,rsize=1048576,wsize=1048576,hard,timeo=600,retrans=2,noresvport 44.216.81.68:/nfsshared   ~/nfsshared/ #Modificar el 44.216.81.68 por la IP elastica del server nfs
sudo emacs -nw /etc/exports #Abre el archivo usando emacs, puedes usar nano o el lector de archivos y debes agregar lo siguiente:
#/nfsshared      *(rw,sync,wdelay,hide,no_subtree_check,sec=sys,insecure,no_root_squash,no_all_squash) esto permite el acceso de lectura y escritura sobre la carpeta
sudo exportfs -a #aplica los cambios
sudo systemctl restart nfs # reinicia el nfs
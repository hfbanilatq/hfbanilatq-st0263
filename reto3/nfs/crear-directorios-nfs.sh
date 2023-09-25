sudo mkdir -p /nfsshared
sudo emacs -nw /etc/exports #Abre el archivo usando emacs, puedes usar nano o el lector de archivos y debes agregar lo siguiente:
#/nfsshared      *(rw,sync,wdelay,hide,no_subtree_check,sec=sys,insecure,no_root_squash,no_all_squash) esto permite el acceso de lectura y escritura sobre la carpeta
sudo exportfs -a #aplica los cambios
sudo systemctl restart nfs # reinicia el nfs
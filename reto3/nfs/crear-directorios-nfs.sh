sudo yum update -y
sudo yum install nfs-utils -y
sudo systemctl enable nfs-server
sudo systemctl start nfs-server
sudo mkdir -p /var/www/html
sudo chown ec2-user:ec2-user -R /var/www #se debe cambiar el dueño de la carpeta sino drupal y docker no podrán conectarse
sudo emacs -nw /etc/exports #Abre el archivo usando emacs, puedes usar nano o el lector de archivos y debes agregar lo siguiente:
#/var/www/html      *(rw,sync,wdelay,hide,no_subtree_check,sec=sys,insecure,no_root_squash,no_all_squash) esto permite el acceso de lectura y escritura sobre la carpeta
sudo systemctl enable nfs-server
sudo exportfs -a #aplica los cambios
sudo systemctl restart nfs # reinicia el nfs
sudo showmount -e #verificar que aparezca la exportacion
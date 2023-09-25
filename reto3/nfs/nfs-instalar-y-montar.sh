sudo yum update -y
sudo yum install nfs-utils -y
sudo mkdir /nfs-share
sudo chown nobody:nobody /nfs-share
sudo chmod 777 /nfs-share

# Luego debes editar el archivo /etc/exports y añadir lo siguiente: /nfs-share IP_DEL_CLIENTE(rw,sync,no_subtree_check) 
# reemplaza el IP_DEL_CLIENTE por la ip dodne tengas el drupal si tienes mas de una instancia debes añadir los registros
# de cada una
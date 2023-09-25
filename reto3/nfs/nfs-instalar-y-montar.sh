sudo yum update -y
sudo yum install nfs-utils -y
sudo mkdir ~/nfsshared
sudo chown nobody:nobody ~/nfsshared
sudo chmod 777 ~/nfsshared
sudo mount -t nfs -o nfsvers=4.1,rsize=1048576,wsize=1048576,hard,timeo=600,retrans=2,noresvport 44.216.81.68:/nfsshared   ~/nfsshared/

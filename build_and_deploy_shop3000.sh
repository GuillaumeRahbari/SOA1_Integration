echo "assuming servicemix is already running and that you have an apache-servicemix-6.0.0 folder in your home folder"
echo "also, this script must be run at the SOA1_Integration root directory"

mvn clean package -rf :Shop3000
cp Shop3000/target/Shop3000-1.0-SNAPSHOT.jar ~/Applications/apache-servicemix-6.0.0/deploy/

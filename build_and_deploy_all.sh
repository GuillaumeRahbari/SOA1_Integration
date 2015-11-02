echo "assuming servicemix is already running and that you have an apache-servicemix-6.0.0 folder in your home folder"
echo "also, this script must be run at the SOA1_Integration root directory"

mvn clean package
cp AllHailBeer/target/beerShop-1.0.jar ~/Downloads/apache-servicemix-6.0.0/deploy/
cp Biko/target/ws-biko-1.0.jar ~/Downloads/apache-servicemix-6.0.0/deploy/
cp VolleyOnTheBeach/target/volley-1.0.jar ~/Downloads/apache-servicemix-6.0.0/deploy/
cp Shop3000/target/Shop3000-1.0-SNAPSHOT.jar ~/Downloads/apache-servicemix-6.0.0/deploy/
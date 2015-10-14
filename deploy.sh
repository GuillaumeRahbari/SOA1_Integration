ssh tom@37.187.126.101 <<EOF

git pull ci master

maven clean package 

cp /home/tom/projects/S9/SOA/Integration/SOA1_Integration/AllHailBeer/target/beerShop-1.0.jar \
	/home/tom/projects/S9/SOA/Integration/SOA1_Integration/Biko/target/ws-biko-1.0.jar \
	/home/tom/projects/S9/SOA/Integration/SOA1_Integration/VolleyOnTheBeach/target/volley-1.0.jar \
	/home/tom/projects/S9/SOA/apache-servicemix-6.0.0/deploy

cd /home/tom/projects/S9/SOA/apache-servicemix-6.0.0/bin/
./servicemix &

exit
EOF

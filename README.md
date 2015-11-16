# Installation steps

1. Open a shell into the code/ directory - you should find here 4 source folders, one inputFiles folder and a pom.xml

2. Type and execute the "maven clean package" command - it you should print "maven build success"

3. Copy the following files into your apache-servicemix/deploy folder:
	- AllHailBeer/target/beerShop-1.0.jar
	- Biko/target/ws-biko-1.0.jar
	- VolleyOnTheBeack/target/volley-1.0.jar
	- Shop3000/target/Shop3000-1.0-SNAPSHOT.jar

4. Start servicemix if not already started

5. The project is ready to be demonstrated! :-)

# Demonstration scenarios

You can now import the soapui project into Soapui and execute the demonstration scenarios but if you want to try it yourself, here's an example demonstration scenario for you:

1. First, register a new client by sending the following HTTP request:
	- method: POST
	- URI: localhost:8181/shop3000/clientFile
	- request body: {"firstName": "Joey", "lastName": "Starr"}

	The response status and body should be "200 OK" and "Client added to database".

2. Now, get Shop 3000's catalog:
	- method: GET
	- URI: localhost:8181/shop3000/catalog

	The response status and should be "200 OK" and a JSON list of JSON objects representing items like the following one:

	{"price":100.0,"name":"bike2","description":{"color":"blue","idBiko":133456789}}

3. Time to add these wonderful products from different brands to the new client's cart! Execute the two next HTTP requests:
	- method: PUT
	- URI: localhost:8181/shop3000/Joey/cart
	- request body: {"price":100.0,"name":"bike2","description":{"color":"blue","idBiko":133456789}}
	- method: PUT
	- URI: localhost:8181/shop3000/Joey/cart
	- request body: {"name":"net","price":9.5,"description":{"color":"BLUE"}}

	For both requests, the HTTP response status should be 200.

4. Everything has a price! You have to send Joey's payment information in order to buy these products:
	- method: POST
	- URI: localhost:8181/shop3000/Joey/payment
	- request body: {"cardNumber": "1111222233334444", "expirationDate":"01/99", "securityCode":"456", "address":"Saint-Denis"}

	The response may be "200 Payment accepted" or "400 Payment refused" whether the payment was successful or not.

5. Congratulations! You just ordered items in two different shops through a single interface!

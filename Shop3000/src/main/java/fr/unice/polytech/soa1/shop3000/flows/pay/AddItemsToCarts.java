package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Laureen Ginier
 * This flows adds the ordered items to the client AllHailBeer cart.
 */
public class AddItemsToCarts extends RouteBuilder {

    private MyProcessor prepareBeerItems = new MyProcessor();
    private MyProcessor prepareBikoItems = new MyProcessor();
    private MyProcessor prepareVolleyItems = new MyProcessor();

    @Override
    public void configure() throws Exception {
        from(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction())
                .log("Starting adding items to AllHailBeer cart")
                .setBody(constant(true));

                //.log("${property.clientID}")
                //.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                //.setBody(constant(""))
                //.process(prepareBeerItems)
                //TODO get client's shop3000 basket using his id
                //TODO get list of items coming from AllHailBeer
                //TODO form a json object with user id, list of items and quantity
                //TODO add items to user AllHailBeer basket => REST request
                //.to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true");
                //.to(Endpoint.PAY_TO_BEER.getInstruction());

        from(Endpoint.ADD_TO_CART_BIKO.getInstruction())
                .log("Starting adding items to Biko cart")
                .setBody(constant(true));

        //.log("${property.clientID}")
                        //.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                        //.setBody(constant(""))
                //.process(prepareBikoItems)
                //TODO get client's shop3000 basket using his id
                //TODO get list of items coming from AllHailBeer
                //TODO form a json object with user id, list of items and quantity
                //TODO add items to user Biko basket => REST request
                //.to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true");
                //.to(Endpoint.PAY_TO_BIKO.getInstruction());

        from(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("Starting adding items to VolleyOnTheBeach cart")
                .setBody(constant(true));

                //.log("${property.clientID}")
                        //.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                        //.setBody(constant(""))
                //.process(prepareVolleyItems)
                //TODO get client's shop3000 basket using his id
                //TODO get list of items coming from AllHailBeer
                //TODO form a json object with user id, list of items and quantity
                //TODO add items to user Volley basket => REST request
                //.to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true");
                //.to(Endpoint.PAY_TO_VOLLEY.getInstruction());
    }
}

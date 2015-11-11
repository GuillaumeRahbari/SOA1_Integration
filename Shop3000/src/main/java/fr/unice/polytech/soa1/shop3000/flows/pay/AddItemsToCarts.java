package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Cart;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayProperties;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.Shop;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * @author Laureen Ginier
 * This flows adds the ordered items to the client AllHailBeer cart.
 */
public class AddItemsToCarts extends RouteBuilder {

    private prepareAddItemBeer prepareAddItemBeer = new prepareAddItemBeer();
    private prepareAddItemBiko prepareAddItemBiko = new prepareAddItemBiko();
    private prepareAddItemVolley prepareAddItemVolley = new prepareAddItemVolley();

    @Override
    public void configure() throws Exception {
        from(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction())
                .log("Starting adding items to AllHailBeer cart")

                .log("${property.clientID}")
/*                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setBody(constant(""))
                .process(addItemToBeer)

                //TODO get client's shop3000 basket using his id
                //TODO get list of items coming from AllHailBeer
                //TODO form a json object with user id, list of items and quantity
                //TODO add items to user AllHailBeer basket => REST request
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")*/
        ;





        from(Endpoint.ADD_TO_CART_BIKO.getInstruction())
                .log("Starting adding items to Biko cart")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .process(prepareAddItemBiko)
                .log("${property.clientID}")
                .log("${property.numberOfItem}")
                .log("Before the loop")
                .loop(simple("${property.numberOfItem}"))
               /*     .setBody().constant("  {\n" +
                    "    \"name\": \"bike2\",\n" +
                    "    \"id\": 133456789,\n" +
                    "    \"color\": \"blue\",\n" +
                    "    \"price\": 100\n" +
                    "  }")*/
                    .log("Yop")
                    .recipientList(simple(("http://localhost:8181/cxf/biko/user/${property.bikoId}/cart/item?bridgeEndpoint=true")))
                    .log("hey")
                .end()
                .log("end of the loop");




        from(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("Starting adding items to VolleyOnTheBeach cart")

                .log("${property.clientID}")
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setBody(constant(""))
                .process(prepareAddItemVolley)
                //TODO get client's shop3000 basket using his id
                //TODO get list of items coming from AllHailBeer
                //TODO form a json object with user id, list of items and quantity
                //TODO add items to user Volley basket => REST request
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")
        ;
    }


    /**
     * This process will create the JSON with all the beer that the client have added to his cart.
     */
    private class prepareAddItemBeer implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String clientId = (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            Client client = ClientStorage.read(clientId);
            if(client != null) {
                HashMap<String, List<CatalogItem>> cart = client.getCart();
                List<CatalogItem> beerItem = cart.get(Shop.BEER.getName());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("owner",clientId);

                JSONObject beerList = new JSONObject();

                for(CatalogItem beer : beerItem) {
                     beerList.put(beer.getName(), beer.getPrice());
                }

                jsonObject.put("cartData", beerList);
                System.out.println(jsonObject);
            }
            exchange.getIn().setBody(constant(false));
        }
    }

    /**
     * This process will create the JSON with all the bike to add to the biko system
     */
    private class prepareAddItemBiko implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String username = (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            Client client = ClientStorage.read(username);

            Cart cart = client.getCart();
            System.out.println(cart.size());
            List<CatalogItem> catalogItemBiko = cart.get(Shop.BIKO.getName());

            exchange.setProperty("numberOfItem",catalogItemBiko.size());
            exchange.setProperty("bikoId", constant(client.getBikoId()));
        }
    }

    /**
     * This process will create the JSON with all the item to add to the volley cart.
     */
    private class prepareAddItemVolley implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            // TODO :
        }
    }

}

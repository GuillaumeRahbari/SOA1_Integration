package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Cart;
import fr.unice.polytech.soa1.shop3000.business.catalog.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayProperties;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.Shop;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.StringWriter;
import java.util.List;

/**
 * @author Laureen Ginier
 * This flows adds the ordered items to the client AllHailBeer cart.
 */
public class AddItemsToCarts extends RouteBuilder {

    private prepareAddItemBeer prepareAddItemBeer = new prepareAddItemBeer();
    private prepareAddItemBiko prepareAddItemBiko = new prepareAddItemBiko();

    private BodyToJonVolley BodyToJonVolley = new BodyToJonVolley();
    private addJsonInBodyBiko addJsonInBodyBiko = new addJsonInBodyBiko();
    private BodyInJsonBeer bodyInJsonBeer = new BodyInJsonBeer();


    @Override
    public void configure() throws Exception {
        from(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction())
                .log("Starting adding items to AllHailBeer cart")
                .log("${property.clientID}")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .process(prepareAddItemBeer)
                .recipientList(simple(("http://localhost:8181/cxf/shop/cart?username=${property.clientID}")))

                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .process(bodyInJsonBeer)
                .recipientList(simple(("http://localhost:8181/cxf/shop/cart?username=${property.clientID}")))

                .log("the end")
        ;


        from(Endpoint.ADD_TO_CART_BIKO.getInstruction())
                .log("Starting adding items to Biko cart")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .process(prepareAddItemBiko)
                .log("${property.clientID}")
                .log("${property.numberOfItem}")
                .log("Before the loop")
                .loop(simple("${property.numberOfItem}"))
                    .log("${property.iterationNumber}")

                    .process(addJsonInBodyBiko)
                    .log("Yop")
                    .recipientList(simple(("http://localhost:8181/cxf/biko/user/${property.bikoId}/cart/item?bridgeEndpoint=true")))
                    .log("hey")
                .end()
                .log("end of the loop");




        from(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("Starting adding items to VolleyOnTheBeach cart")

                .log("${property.clientID}")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setBody(constant(""))
                .process(BodyToJonVolley)
                .log("${property.clientID}")
               // .log("${body}")
                .recipientList(simple("http://localhost:8181/cxf/volley/basket/${property.clientID}?bridgeEndpoint=true"));

    }


    /**
     * This process will create the JSON with all the beer that the client have added to his cart.
     */
    private class prepareAddItemBeer implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String user = (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("owner",user);
            jsonObject.put("cartData", new JSONObject());

            exchange.getIn().setBody(jsonObject.toString());
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
            List<CatalogItem> catalogItemBiko = cart.get(Shop.BIKO.getName());

            exchange.setProperty("numberOfItem",catalogItemBiko.size());
            exchange.setProperty("bikoId", constant(client.getBikoId()));
            exchange.setProperty("iterationNumber", 0);
        }
    }

    /**
     * This process will create the JSON with all the item to add to the volley cart.
     */
    private class BodyToJonVolley implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String clientName = (String)exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            Client client = ClientStorage.read(clientName);

            Cart cart = client.getCart();
            List<CatalogItem> volleyItems = cart.get(Shop.VOLLEY.getName());
            JSONArray jsonArray = new JSONArray();
            for(CatalogItem catalogItem : volleyItems) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("color", catalogItem.getDescription().getColor());
                jsonObject.put("quantity",1);
                jsonObject.put("name",catalogItem.getName());
                jsonArray.add(jsonObject);
            }
            StringWriter out = new StringWriter();
            jsonArray.writeJSONString(out);
            exchange.getIn().setBody(out.toString());
        }
    }


    private class addJsonInBodyBiko implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            int iterationNumber = (int)exchange.getProperty("iterationNumber");
            String clientName = (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());

            Client client = ClientStorage.read(clientName);
            Cart cart = client.getCart();
            List<CatalogItem> catalogItems = cart.get(Shop.BIKO.getName());
            CatalogItem catalogItem = catalogItems.get(iterationNumber);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", catalogItem.getName());
            jsonObject.put("id", catalogItem.getDescription().getIdBiko());
            jsonObject.put("color", catalogItem.getDescription().getColor());
            jsonObject.put("price", catalogItem.getPrice());

            exchange.getIn().setBody(jsonObject.toString());
            exchange.setProperty("iterationNumber", iterationNumber++);
        }
    }


    private class BodyInJsonBeer implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String clientName = (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            Client client = ClientStorage.read(clientName);

            List<CatalogItem> beerCart = client.getCart().get(Shop.BEER.getName());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("owner",clientName);

            for(CatalogItem catalogItem1 : beerCart) {
                JSONObject beer = new JSONObject();
                beer.put(catalogItem1.getName(),catalogItem1.getDescription().getQuantite());
                jsonObject.put("cartData", beer);
            }
            exchange.getIn().setBody(jsonObject.toString());

        }
    }
}

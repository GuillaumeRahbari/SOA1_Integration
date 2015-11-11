package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayProperties;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;

/**
 * Created by user on 02/11/2015.
 */
public class CheckClientFlow extends RouteBuilder {

    private CheckBikoClientExistence checkClientExistenceBikoBiko;
    private CheckBeerClientExistence checkClientExistenceBeer;
    private CheckVolleyClientExistence checkClientExistenceVolley;

    public CheckClientFlow(){
        this.checkClientExistenceBikoBiko = new CheckBikoClientExistence();
        this.checkClientExistenceBeer = new CheckBeerClientExistence();
        this.checkClientExistenceVolley = new CheckVolleyClientExistence();
    }

    @Override
    public void configure() throws Exception {

        /**
         * This flow check if the client is already registered in the HailBeer system.
         * If the client is not registered then we create the client in the system.
         * And finally we add the item to the cart
         */
        from(Endpoint.CHECK_CLIENT_BEER.getInstruction())
                .log("Begin check client beer")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .log("hello")
                .log("${property.clientID}")
                .doTry()
                    .recipientList(simple("http://localhost:8181/cxf/shop/account/${property.clientID}/${property.clientID}?bridgeEndpoint=true")).end()
                .doCatch(IOException.class, IllegalStateException.class, Exception.class)
                    .log("catch")
                            .to(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction())
                        /** {@link CheckBeerClientExistence} **/
                .process(checkClientExistenceBeer)
                .choice()
                    .when(simple("${property.result} == true"))
                    .when(simple("${property.result} == false"))
                        .to(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction())
                .end();
       //         .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction());

        /**
         * This flow check if the client is in the biko system, if not we create the client in the system and then
         * we add the items to the the cart.
         */
        from(Endpoint.CHECK_CLIENT_BIKO.getInstruction())
                .log("Begin check client biko")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .doTry()
                .recipientList(simple("http://localhost:8181/cxf/biko/clients/name/${property.clientID}?bridgeEndpoint=true")).end()
                .doCatch(Exception.class)
                    .log("catch")
                /** {@link CheckBikoClientExistence} **/
                .process(checkClientExistenceBikoBiko)
                .choice()
                    .when(simple("${property.result} == true"))
                    .when(simple("${property.result} == false"))
                        .to(Endpoint.CREATE_CLIENT_BIKO.getInstruction())
                .end()
                .to(Endpoint.ADD_TO_CART_BIKO.getInstruction());

        /**
         * This flow check if client is already registered in the volley system. Create the client otherwise.
         * Then add the items to the cart.
         */
        from(Endpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .log("Begin check client volley")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .doTry()
                .recipientList(simple("http://localhost:8181/cxf/volley/accounts/${property.clientID}?bridgeEndpoint=true")).end()
                .doCatch(Exception.class)
                        .log("catch")
                /** @{Link CheckClientExistenceVolley} **/
                .process(checkClientExistenceVolley)
                .choice()
                    .when(simple("${property.result} == true"))
                    .when(simple("${property.result} == false"))
                        .to(Endpoint.CREATE_CLIENT_VOLLEY_ON_THE_BEACH.getInstruction())
                .end();
           //     .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction());
    }


    private class CheckVolleyClientExistence extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            String loginToTest = (String)exchange.getProperty("clientID");
            String body = (String) exchange.getIn().getBody();
            if(body.equals("")) {
                exchange.setProperty("result", false);
            } else {
                String login = new JSONObject(body).getString("login");
                if (loginToTest.equals(login)) {
                    exchange.setProperty("result", true);
                } else {
                    exchange.setProperty("result", false);
                }
            }
        }
    }


    private class CheckBikoClientExistence extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            //test if client exist
            String nameToTest = (String)exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction());
            String body = (String) exchange.getIn().getBody();
            if(body.equals("")) {
                exchange.setProperty("result",false);
            } else {
                JSONObject jObject = new JSONObject(body);
                String name = jObject.getString("name");

                if (nameToTest.equals(name))
                    exchange.setProperty("result", true);
                else
                    exchange.setProperty("result", false);
            }
        }
    }


    private class CheckBeerClientExistence extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            // test if client exist
            String body = extractExchangeBody(exchange);
            String loginToTest = (String)exchange.getProperty("clientID");
            String login = new JSONArray(body).getJSONObject(0).getJSONObject(loginToTest).getString("username");

            if(loginToTest.equals(login)) {
                exchange.setProperty("result", true);
            }else {
                exchange.setProperty("result", false);
            }
        }
    }
}

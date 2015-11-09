package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by user on 02/11/2015.
 */
public class CheckClientFlow extends RouteBuilder {
    private CheckClientExistenceBiko checkClientExistenceBikoBiko;
    private CheckClientExistenceBeer checkClientExistenceBeer;
    private CheckClientExistenceVolley checkClientExistenceVolley;

    public CheckClientFlow(){
        this.checkClientExistenceBikoBiko = new CheckClientExistenceBiko();
        this.checkClientExistenceBeer = new CheckClientExistenceBeer();
        this.checkClientExistenceVolley = new CheckClientExistenceVolley();
    }

    @Override
    public void configure() throws Exception {
        /**
         * This flow check if the client is already registered in the HailBeer system.
         * If the client is not registered then we create the client in the system.
         * And finally we add the item to the cart
         */
        from(Endpoint.CHECK_CLIENT_BEER.getInstruction())
                .log("Begin check client")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .log("${property.clientID}")
                .recipientList(simple("http://localhost:8181/cxf/shop/account/${property.clientID}/${property.clientID}?bridgeEndpoint=true"))
                        /** {@link CheckClientExistenceBeer} **/
                    .process(checkClientExistenceBeer)
                    .choice()
                        .when(simple("${property.result} == true"))
                            .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction())
                        .when(simple("${property.result} == false"))
                            .to(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction());

        /**
         * This flow check if the client is in the biko system, if not we create the client in the system and then
         * we add the items to the the cart.
         */
        from(Endpoint.CHECK_CLIENT_BIKO.getInstruction())
                .log("Begin check client")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .recipientList(simple("http://localhost:8181/cxf/biko/clients/name/${property.clientID}?bridgeEndpoint=true"))

                /** {@link CheckClientExistenceBiko} **/
                    .process(checkClientExistenceBikoBiko)
                    .choice()
                        .when(simple("${property.result} == true"))
                            .to(Endpoint.ADD_TO_CART_BIKO.getInstruction())
                        .when(simple("${property.result} == false"))
                            .to(Endpoint.CREATE_CLIENT_BIKO.getInstruction());

        /**
         * This flow check if client is already registered in the volley system. Create the client otherwise.
         * Then add the items to the cart.
         */
        from(Endpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .log("Begin check client")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .recipientList(simple("http://localhost:8181/cxf/volley/accounts/${property.clientID}?bridgeEndpoint=true"))

                /** @{Link CheckClientExistenceVolley} **/
                    .process(checkClientExistenceVolley)
                    .choice()
                        .when(simple("${property.result} == true"))
                            .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction())
                        .when(simple("${property.result} == false"))
                            .to(Endpoint.CREATE_CLIENT_VOLLEY_ON_THE_BEACH.getInstruction());
    }
}

package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by tom on 26/10/15.
 * Updated by Laureen on 11/10/2015.
 */
public class CartRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        /**
         * Begin of the flow to add an item to the cart of the client
         *
         * {@link Unmarshaller}
         */
        rest("{clientID}/cart")
                .put()
                .to(Endpoint.UNMARSHALL_JSON_ITEM.getInstruction());

        /**
         * Begin of the flow to get the cart of the client
         *
         * {@link GetCart}
         */
        rest("{clientID}/cart")
                .get()
                .to(Endpoint.GET_CART.getInstruction());

        /**
         * End of the flow started by : PUT /{clientID]/cart
         * Comes from {@link CartFlows}
         */
        from(Endpoint.CHECK_REQUEST_STATUS.getInstruction())
                .log("ok")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${property.status}"));

    }
}

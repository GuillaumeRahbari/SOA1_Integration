package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by tom on 26/10/15.
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
         * End of the flow started by : PUT /{clientID]/cart
         * Comes from {@link CartFlows}
         */
        from(Endpoint.CHECK_REQUEST_STATUS.getInstruction())
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${property.status}"));


        /**
         * Juste du test : va disparaitre
         */
        rest("/clients/createBiko")
                .post()
                .to(Endpoint.CREATE_CLIENT_BIKO.getInstruction());

    }
}

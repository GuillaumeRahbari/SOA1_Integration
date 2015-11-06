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
         * {@link CartFlows}
         */
        rest("{clientID}/cart")
                .put()
                .to(Endpoint.UNMARSHALL_JSON_ITEM.getInstruction());

        rest("/clients/createBiko")
                .post()
                .to(Endpoint.CREATE_CLIENT_BIKO.getInstruction());

        
        // Defining what we do after we checked the status. We set the header with the good status.

        from(Endpoint.CHECK_REQUEST_STATUS.getInstruction())
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${property.status}"));

    }
}

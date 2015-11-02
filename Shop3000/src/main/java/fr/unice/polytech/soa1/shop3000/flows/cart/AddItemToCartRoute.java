package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by tom on 26/10/15.
 */
public class AddItemToCartRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Binding the REST domain specific language to the Servlet component
        restConfiguration().component("servlet"); // feature:install camel-servlet + edit in the OSGi blueprint

        // Defining the resource to expose, and the used verb
        rest("{clientID}/cart")
                .put()
                .to(Endpoint.UNMARSHALL_JSON_ITEM.getInstruction());



        from(Endpoint.CHECK_REQUEST_STATUS.getInstruction())
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("${property.status}"));


        // Expose the cart of a given client
        rest("{clientID}/cart")
                .get()
                .to(Endpoint.GET_CART.getInstruction());
    }
}

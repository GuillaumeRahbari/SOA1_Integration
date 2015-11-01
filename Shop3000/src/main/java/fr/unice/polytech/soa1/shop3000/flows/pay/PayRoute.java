package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 */
public class PayRoute extends RouteBuilder {

    private static final String GET_CLIENT_FROM_REST_ENDPOINT = "direct:getClientFromRest";

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("{clientID}/payment") // TODO capturer le PATH param
            .post()
            .to(GET_CLIENT_FROM_REST_ENDPOINT);

        from(GET_CLIENT_FROM_REST_ENDPOINT)
            .log("client: ${header.clientID}")
            .setBody(simple("${body}\n${header.clientID}"))
            .log("body: ${body}")
            .to(Endpoint.VALIDATE_CART.getInstruction());
    }
}

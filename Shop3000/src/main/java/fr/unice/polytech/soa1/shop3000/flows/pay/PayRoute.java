package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 */
public class PayRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("clientID/payment") // TODO capturer le PATH param
            .post()
                .to(Endpoint.VALIDATE_CART.getInstruction());
    }
}

package fr.unice.polytech.soa1.shop3000.flows.pay;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * PayEndpoint for the payment flow.
 * The route is {host}:{port}/{appname}/{clientID}/payment and the expected method is POST
 * Expects a JSON in POST body representing a PaymentInformation object.
 */
public class PayRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("{clientID}/payment")
            .post()
                    /** {@link PayUnmarshaller#configure() next} flow **/
                .to(PayEndpoint.UNMARSHAL.getInstruction());
    }
}

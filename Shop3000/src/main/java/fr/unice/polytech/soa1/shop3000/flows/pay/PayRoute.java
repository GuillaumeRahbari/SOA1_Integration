package fr.unice.polytech.soa1.shop3000.flows.pay;

import org.apache.camel.Exchange;
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

        /**
         * Flow sending a BadRequest HTTP response in case of bad payment information.
         */
        from(PayEndpoint.BAD_PAYMENT_INFORMATION_ENDPOINT.getInstruction())
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody(constant("Bad payment information."));
    }
}

package fr.unice.polytech.soa1.shop3000.flows.pay;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Endpoint for the payment flow.
 * The route is {host}:{port}/{appname}/{clientID}/payment and the expected method is POST
 * Expects a JSON in POST body representing a PaymentInformation object.
 */
public class PayRoute extends RouteBuilder {

    private static final String GET_CLIENT_FROM_REST_ENDPOINT = "activeMQ:getClientFromRest";
    static final String PAYMENT_INFORMATION_PROPERTY = "paymentInformation",
                        CLIENT_ID_PROPERTY = "clientID";

    private JsonPaymentInformationExtractor jsonPaymentInformationExtractor = new JsonPaymentInformationExtractor();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("{clientID}/payment")
            .post()
            .to(GET_CLIENT_FROM_REST_ENDPOINT);

        from(GET_CLIENT_FROM_REST_ENDPOINT)
            .log("extracting POST data")
            .setProperty(PAYMENT_INFORMATION_PROPERTY, body())
            .process(jsonPaymentInformationExtractor)
            .setProperty(CLIENT_ID_PROPERTY, constant("${header.clientID}"))
            .log("client: ${" + CLIENT_ID_PROPERTY + "}")
            // {@link ValidateCartAndPayment#configure() next} flow
            .to(Endpoint.VALIDATE_CART.getInstruction());
    }
}

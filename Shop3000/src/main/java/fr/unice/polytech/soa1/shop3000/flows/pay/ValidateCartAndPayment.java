package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 */
public class ValidateCartAndPayment extends RouteBuilder {

    private static final String BAD_PAYMENT_INFORMATION_ENDPOINT = "direct:badPaymentInformation";

    @Override
    public void configure() throws Exception {

        /** Flow checking payment information.
         *  Expects a property "paymentInformation" with a JSON representing a PaymentInformation object.
         */
        from(PayEndpoint.VALIDATE_PAYMENT_INFORMATION.getInstruction())
                .log("starting payment information checking")
                .choice()
                    .when(new Predicate() {
                        public boolean matches(Exchange exchange) {
                            return exchange.getProperty(PayUnmarshaller.PAYMENT_INFORMATION_PROPERTY)
                                    .equals(PayUnmarshaller.BAD_INFORMATION);
                        }
                    })
                        .log("bad information")
                        .to(BAD_PAYMENT_INFORMATION_ENDPOINT)
                    .otherwise()
                        .log("good information")
                        .to(PayEndpoint.VALIDATE_CART.getInstruction())
                .endChoice();

        from(BAD_PAYMENT_INFORMATION_ENDPOINT)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody(constant("Bad payment information."));

        from(PayEndpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
                .log("body: ${body}")
                // TODO extract payment info from body and set a property

                .wireTap(PayEndpoint.UPDATE_BEST_SELLER.getInstruction())

                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy()) // TODO c'etait une autre strat d'aggreg
                    .log("multicasting")
                    .to(PayEndpoint.CHECK_CLIENT_BEER.getInstruction())
                    .to(PayEndpoint.CHECK_CLIENT_BIKO.getInstruction())
                    .to(PayEndpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .log("merging")
                .end()
                .log("body: ${body}");
                // TODO extract payment info from property and set body
                //.to(PayEndpoint.PAY.getInstruction());


        /**
         * This flow handle the best seller
         */
        from(PayEndpoint.UPDATE_BEST_SELLER.getInstruction())
                .log("Here we update the number of item sells for the best seller");

    }
}

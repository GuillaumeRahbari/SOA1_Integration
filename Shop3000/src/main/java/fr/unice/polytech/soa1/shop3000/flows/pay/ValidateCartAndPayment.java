package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for checking payment information and cart contents.
 */
public class ValidateCartAndPayment extends RouteBuilder {

    private CartExtractor cartExtractor = new CartExtractor();

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
                        /** {@link PayRoute#configure() next} route builder **/
                        .to(PayEndpoint.BAD_PAYMENT_INFORMATION_ENDPOINT.getInstruction())
                    .otherwise()
                        .log("good information")
                        /** {@link ValidateCartAndPayment#configure() next} route builder **/
                        .to(PayEndpoint.VALIDATE_CART.getInstruction())
                .endChoice();

        /**
         * Flow extracting cart and validating its content by sending to the shops their related products.
         */
        from(PayEndpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
                .log("starting cart extraction")
                .process(cartExtractor)
                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy()) // TODO c'etait une autre strat d'aggreg
                    .log("multicasting")
                    .to(PayEndpoint.CHECK_CLIENT_BEER.getInstruction())
                  //  .to(PayEndpoint.CHECK_CLIENT_BIKO.getInstruction())
                  //  .to(PayEndpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .log("merging")
                .end()
                .log("body: ${body}");
                // TODO extract payment info from property and set body
                //.to(PayEndpoint.PAY.getInstruction());
    }

    /**
     * Process responsible for extracting a client cart.
     * Expects a client id to be set in the "clientID" exchange property.
     * Sets a "cart" exchange property.
     */
    private class CartExtractor extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {

        }
    }
}

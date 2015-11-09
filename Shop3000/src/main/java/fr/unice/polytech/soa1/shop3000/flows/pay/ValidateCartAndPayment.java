package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Cart;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for checking payment information and cart contents.
 */
public class ValidateCartAndPayment extends RouteBuilder {

    public static final String CART_PROPERTY = "cart";

    private CartExtractor cartExtractor = new CartExtractor();

    @Override
    public void configure() throws Exception {

        /** Flow checking payment information.
         *  Expects a property "paymentInformation" with a JSON representing a PaymentInformation object.
         */
        from(PayEndpoint.VALIDATE_PAYMENT_INFORMATION.getInstruction())
                .log("starting payment information checking")
                .choice()
                    .when(exchange -> exchange.getProperty(PayUnmarshaller.PAYMENT_INFORMATION_PROPERTY)
                            .equals(PayUnmarshaller.BAD_INFORMATION))
                        .log("bad payment information")
                        /** {@link PayRoute#configure() next} route builder **/
                        .to(PayEndpoint.BAD_PAYMENT_INFORMATION_ENDPOINT.getInstruction())
                    .otherwise()
                        .log("good payment information")
                        /** {@link ValidateCartAndPayment#configure() next} route builder **/
                        .to(PayEndpoint.EXTRACT_CART.getInstruction())
                .endChoice();

        /**
         * Flow extracting cart.
         */
        from(PayEndpoint.EXTRACT_CART.getInstruction())
                .log("starting cart extraction")
                .process(cartExtractor)
                .choice()
                    .when(exchange -> exchange.getProperty(CART_PROPERTY).equals(PayUnmarshaller.BAD_INFORMATION))
                        .log("bad client id")
                        /** {@link PayRoute#configure() next} route builder **/
                        .to(PayEndpoint.BAD_CLIENT_ID.getInstruction())
                    .otherwise()
                        .log("client id OK")
                        /** {@link ValidateCartAndPayment#configure() next} route builder **/
                        .to(PayEndpoint.VALIDATE_CART.getInstruction())
                .endChoice();

        /**
         * Flow extracting cart and validating its content by sending to the shops their related products.
         */
        from(PayEndpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
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
                // TODO guigui
    }

    /**
     * Process responsible for extracting a client cart.
     * Expects a client id to be set in the "clientID" exchange property.
     * Sets a "cart" exchange property to a cart object if the client exists, otherwise sets this property to "".
     */
    private class CartExtractor extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Client client = ClientStorage.read((String) exchange.getProperty(PayUnmarshaller.CLIENT_ID_PROPERTY));

            if (client != null) {
                Cart cart = client.getCart();

                if (cart != null) {
                    exchange.setProperty(CART_PROPERTY, cart);
                }
                else {
                    exchange.setProperty(CART_PROPERTY, PayUnmarshaller.BAD_INFORMATION);
                }
            }
            else {
                exchange.setProperty(CART_PROPERTY, PayUnmarshaller.BAD_INFORMATION);
            }
        }
    }
}

package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayEndpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for proceeding to shops payment.
 * Each flow expects the exchange body to be set to the clientID.
 */
public class PayShop extends RouteBuilder {

    private static final String BEER_PAYMENT_SERVICE_ADDRESS = "http://localhost:8181/cxf/shop/cart/validation",
        BIKO_CART_SERVICE_ADDRESS = "http://localhost:8181/cxf/biko/user/",
        VOLLEY_PAYMENT_SERVICE_ADDRESS = "http://localhost:8181/cxf/volley/payments/", // needs account id path param
        BIKO_SHOP3000_PAYMENT_INFORMATION = "{\"creditCardNumber\":0, \"securityCode\":1, \"expirationDate\":2012}",
        VOLLEY_SHOP3000_PAYMENT_INFORMATION = "{\"cardNumber\":\"0\", \"crypto\":\"1\", \"validityDate\":\"20/12\"," +
                "\"address\":\"shop3000\"}";

    private GetBikoClientID getBikoClientID = new GetBikoClientID();

    @Override
    public void configure() throws Exception {

        /**
         * Flow proceeding to All Hail Beer payment.
         */
        from(PayEndpoint.PAY_BEER.getInstruction())
            .log("paying All Hail Beer")
            .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
            .setHeader(Exchange.HTTP_PATH, simple("?username=${body}&cb=0"))
            .to(BEER_PAYMENT_SERVICE_ADDRESS)
            .setBody(constant(true)); // TODO may fail

        /**
         * Flow proceeding to Biko payment.
         */
        from(PayEndpoint.PAY_BIKO.getInstruction())
            .log("paying Biko")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .process(getBikoClientID)
            .setHeader(Exchange.HTTP_PATH, simple("${body}/cart"))
            .setBody(constant(BIKO_SHOP3000_PAYMENT_INFORMATION))
            .to(BIKO_CART_SERVICE_ADDRESS)
            .setBody(constant(true)); // TODO may fail

        /**
         * Flow proceeding to Volley on the Beach payment.
         */
        from(PayEndpoint.PAY_VOLLEY.getInstruction())
                .log("paying Volley On The Beach")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_PATH, simple("${body}"))
                .setBody(constant(VOLLEY_SHOP3000_PAYMENT_INFORMATION))
                .to(VOLLEY_PAYMENT_SERVICE_ADDRESS)
                .setBody(constant(true)); // TODO may fail...
    }

    /**
     * Extracts a biko client id from a a shop3000 client id.
     * Expects the exchange body to be set to the client id. Resets the body to the resulting biko client id.
     */
    private class GetBikoClientID extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Client bikoClient = ClientStorage.read((String) exchange.getIn().getBody());
            exchange.getIn().setBody(bikoClient.getBikoId());
        }
    }
}

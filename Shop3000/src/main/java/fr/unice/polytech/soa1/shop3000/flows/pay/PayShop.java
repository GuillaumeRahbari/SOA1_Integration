package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.payment.BikoPaymentInformation;
import fr.unice.polytech.soa1.shop3000.business.payment.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.business.payment.VolleyPaymentInformation;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayEndpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for proceeding to shops payment.
 */
public class PayShop extends RouteBuilder {

    private static final String BEER_PAYMENT_SERVICE_ADDRESS = "",
        BIKO_CART_SERVICE_ADDRESS = "http://localhost:8181/cxf/biko/user/",
        VOLLEY_PAYMENT_SERVICE_ADDRESS = "http://localhost:8181/cxf/volley/payments/"; // needs account id path param

    private MakeVolleyPaymentInformation makeVolleyPaymentInformation = new MakeVolleyPaymentInformation();
    private MakeBikoPaymentInformation makeBikoPaymentInformation = new MakeBikoPaymentInformation();

    @Override
    public void configure() throws Exception {

        /**
         * Flow proceeding to All Hail Beer payment.
         */
        from(PayEndpoint.PAY_BEER.getInstruction()).log("lol")
        ; // TODO

        /**
         * Flow proceeding to Biko payment.
         */
        from(PayEndpoint.PAY_BIKO.getInstruction()).log("lol")
            .log("paying Biko")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.HTTP_PATH, simple("${header." + Exchange.HTTP_PATH + "}/cart"))
            .process(makeBikoPaymentInformation)
            .to(BIKO_CART_SERVICE_ADDRESS)
            .setBody(constant(true)); // TODO may fail

        /**
         * Flow proceeding to Volley on the Beach payment.
         */
        from(PayEndpoint.PAY_VOLLEY.getInstruction())
                .log("paying Volley On The Beach")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .process(makeVolleyPaymentInformation)
                .to(VOLLEY_PAYMENT_SERVICE_ADDRESS)
                .setBody(constant(true)); // TODO may fail...
    }

    /**
     * Abstract class for processors which roles are to transform shop3000 payment information objects into shop
     * specific payment information objects.
     * Expects the exchange body to represent a PaymentInformation object.
     */
    private abstract class MakePaymentInformation extends SuperProcessor {

        // Attributes

        protected ObjectMapper mapper = new ObjectMapper();
        protected PaymentInformation information;

        // Methods

        @Override
        public void process(Exchange exchange) throws Exception {
            information = mapper.readValue((String) exchange.getIn().getBody(), PaymentInformation.class);
            transformPaymentInformation(exchange);
        }

        protected abstract void transformPaymentInformation(Exchange exchange) throws Exception;
    }

    /**
     * Transforms a shop3000 payment information object into a Volley on the Beach payment information object.
     * Expects the exchange body to represent a PaymentInformation object. Resets it to the resulting value.
     */
    private class MakeVolleyPaymentInformation extends MakePaymentInformation {

        @Override
        protected void transformPaymentInformation(Exchange exchange) throws Exception {
            VolleyPaymentInformation volleyPaymentInformation = new VolleyPaymentInformation(information);
            exchange.getIn().setBody(mapper.writeValueAsString(volleyPaymentInformation));
        }
    }

    /**
     * Transforms a shop3000 payment information object into a Biko payment information object.
     * Expects the exchange body to represent a PaymentInformation object. Resets it to the resulting value.
     */
    private class MakeBikoPaymentInformation extends MakePaymentInformation {

        @Override
        protected void transformPaymentInformation(Exchange exchange) throws Exception {
            BikoPaymentInformation bikoPaymentInformation = new BikoPaymentInformation(information);
            exchange.getIn().setBody(mapper.writeValueAsString(bikoPaymentInformation));
        }
    }
}

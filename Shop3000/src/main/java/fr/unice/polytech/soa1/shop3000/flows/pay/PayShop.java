package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        BIKO_PAYMENT_SERVICE_ADDRESS = "",
        VOLLEY_PAYMENT_SERVICE_ADDRESS = "http://localhost:8181/cxf/volley/payments/"; // needs account id path param

    private MakeVolleyPaymentInformation makeVolleyPaymentInformation = new MakeVolleyPaymentInformation();

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
            ; // TODO

        /**
         * Flow proceeding to Volley on the Beach payment.
         */
        from(PayEndpoint.PAY_VOLLEY.getInstruction())
                .log("paying Volley On The Beach")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .process(makeVolleyPaymentInformation)
                .to(VOLLEY_PAYMENT_SERVICE_ADDRESS); // TODO
    }

    /**
     * Transforms a shop3000 payment information object into a Volley on the Beach payment information object.
     * Expects the exchange body to represent a PaymentInformation object. Resets it to the resulting value.
     */
    private class MakeVolleyPaymentInformation extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentInformation paymentInformation = objectMapper.readValue((String) exchange.getIn().getBody(),
                    PaymentInformation.class);
            VolleyPaymentInformation volleyPaymentInformation = new VolleyPaymentInformation(paymentInformation);

            exchange.getIn().setBody(objectMapper.writeValueAsString(volleyPaymentInformation));
            System.out.println(exchange.getIn().getBody());
        }
    }
}

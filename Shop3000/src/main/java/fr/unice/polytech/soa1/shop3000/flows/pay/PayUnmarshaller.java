package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for unmarshalling data from payment requests.
 */
public class PayUnmarshaller extends RouteBuilder {

    public static final String PAYMENT_INFORMATION_PROPERTY = "paymentInformation",
            CLIENT_ID_PROPERTY = "clientID", BAD_INFORMATION = "";

    private JsonPaymentInformationExtractor jsonPaymentInformationExtractor = new JsonPaymentInformationExtractor();
    private PrepareWS prepareWS = new PrepareWS();

    @Override
    public void configure() throws Exception {

        /**
         * Expects a JSON in POST body representing a PaymentInformation object and a header set to the client id.
         */
        from(PayEndpoint.UNMARSHAL.getInstruction())
                .log("extracting POST data")
                .setProperty(PAYMENT_INFORMATION_PROPERTY, body())
                .process(jsonPaymentInformationExtractor)
                .setProperty(CLIENT_ID_PROPERTY, simple("${header.clientId}"))
                .log("client: ${property." + CLIENT_ID_PROPERTY + "}")
                        /** {@link ProceedPayment#configure() next} flow **/
                .to(PayEndpoint.VALIDATE_PAYMENT_INFORMATION.getInstruction());


        /**
         * This flow will clear the body for the WS and prepare the property the WS will send to the client
         */
        from(PayEndpoint.PAYMENT_TO_WS.getInstruction())
                .log("Begin of the check payment status")
                .process(prepareWS)
                .log("${property.requestStatus}")

                        /** {@link PayRoute#configure()} **/
                .to(PayEndpoint.END_PAYMENT.getInstruction());


    }

    /**
     * Process responsible for extracting a PaymentInformation object from a JSON expected to be in a
     * "paymentInformation" exchange property.
     * Resets the "paymentInformation" exchange property.
     */
    private class JsonPaymentInformationExtractor extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                PaymentInformation paymentInformation = objectMapper.readValue(
                    /*(String) exchange.getProperty(PayRoute.PAYMENT_INFORMATION_PROPERTY), PaymentInformation.class
            );*/
                        extractExchangeProperty(exchange, PAYMENT_INFORMATION_PROPERTY), PaymentInformation.class);

                exchange.setProperty(PAYMENT_INFORMATION_PROPERTY,
                        objectMapper.writeValueAsString(paymentInformation));
            }
            catch (JsonMappingException e) {
                exchange.setProperty(PAYMENT_INFORMATION_PROPERTY, BAD_INFORMATION);
            }
        }
    }


    private class PrepareWS implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            exchange.getIn().getBody();
            boolean paymentDone = (boolean) exchange.getProperty(ExchangeProperties.PAYMENT_STATE_PROPERTY.getInstruction());
            if(paymentDone) {
                exchange.setProperty("requestStatus",200);
            } else {
                exchange.setProperty("requestStatus",400);
            }
        }
    }

}

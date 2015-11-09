package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for unmarshalling data from payment requests.
 */
public class PayUnmarshaller extends RouteBuilder {

    static final String PAYMENT_INFORMATION_PROPERTY = "paymentInformation",
            CLIENT_ID_PROPERTY = "clientID", BAD_INFORMATION = "";

    private JsonPaymentInformationExtractor jsonPaymentInformationExtractor = new JsonPaymentInformationExtractor();

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
                        /** {@link ValidateCartAndPayment#configure() next} flow **/
                .to(PayEndpoint.VALIDATE_PAYMENT_INFORMATION.getInstruction());
    }

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
}

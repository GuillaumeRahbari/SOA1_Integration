package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;

/**
 * @author Marc Karassev
 */
public class CheckPaymentInformationJson extends SuperProcessor {

    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PaymentInformation paymentInformation = objectMapper.readValue(
                    extractExchangeProperty(exchange, PayRoute.PAYMENT_INFORMATION_PROPERTY), PaymentInformation.class);

            exchange.setProperty(PayRoute.PAYMENT_INFORMATION_PROPERTY,
                    objectMapper.writeValueAsString(paymentInformation));
        }
        catch (JsonMappingException e) {
            exchange.setProperty(PayRoute.PAYMENT_INFORMATION_PROPERTY, "");
        }
    }
}

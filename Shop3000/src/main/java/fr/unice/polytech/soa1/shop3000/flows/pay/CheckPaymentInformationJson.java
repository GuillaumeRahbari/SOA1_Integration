package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author Marc Karassev
 */
public class CheckPaymentInformationJson implements Processor {

    public void process(Exchange exchange) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println((PaymentInformation) exchange.getProperty("paymentInformation"));
        //PaymentInformation paymentInformation = objectMapper.readValue(exchange.getProperty("paymentInformation").toString(), PaymentInformation.class);
    }
}

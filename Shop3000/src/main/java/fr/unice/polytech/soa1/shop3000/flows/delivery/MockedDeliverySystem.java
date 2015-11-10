package fr.unice.polytech.soa1.shop3000.flows.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.flows.pay.PayUnmarshaller;
import fr.unice.polytech.soa1.shop3000.utils.Shop3000Information;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Quentin on 11/10/2015.
 */
public class MockedDeliverySystem implements Processor {

    public static String DELIVERY_PRICE = "deliveryPrice";

    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentInformation paymentInformation =  objectMapper.readValue((String) exchange.getProperty(PayUnmarshaller.PAYMENT_INFORMATION_PROPERTY), PaymentInformation.class);
        String clientAddress = paymentInformation.getAddress();

        int price = externalCallToDeliverySystem(Shop3000Information.ADDRESS, clientAddress);
        exchange.setProperty(DELIVERY_PRICE, price);

    }


    private int externalCallToDeliverySystem(String startAddress, String endAddress) {
        return 666;
    }
}

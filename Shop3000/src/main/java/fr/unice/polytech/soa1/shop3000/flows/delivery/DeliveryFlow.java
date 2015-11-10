package fr.unice.polytech.soa1.shop3000.flows.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.flows.pay.ExchangeProperties;
import fr.unice.polytech.soa1.shop3000.flows.pay.PayEndpoint;
import fr.unice.polytech.soa1.shop3000.flows.pay.ProceedPayment;
import fr.unice.polytech.soa1.shop3000.utils.MockDeliverySystem;
import fr.unice.polytech.soa1.shop3000.utils.Shop3000Information;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 11/10/2015.
 */
public class DeliveryFlow extends RouteBuilder {

    public static String DELIVERY_PRICE_PROPERTY = "deliveryPrice";

    private HandleDeliveryPrice handleDeliveryPrice = new HandleDeliveryPrice();

    @Override
    public void configure() throws Exception {

        from(DeliveryEndpoints.GET_DELIVERY_PRICE.getInstruction())
                .log("Begin of the flow to get the delivery Price")

                        /** {@link HandleDeliveryPrice#process(Exchange)}  **/
                .process(handleDeliveryPrice)

                /** {@link ProceedPayment#configure()}  **/
                .to(PayEndpoint.SHOP3000_PAYMENT.getInstruction())
        ;

    }


    /**
     * This process will add a property where the name is defined in the constant DELIVERY_PRICE_PROPERTY
     * This property will contain the price of the delivery given by the the delivery system.
     */
    private class HandleDeliveryPrice implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentInformation paymentInformation =  objectMapper.readValue(
                    (String) exchange.getProperty(ExchangeProperties.PAYMENT_INFORMATION_PROPERTY.getInstruction()), PaymentInformation.class);
            String clientAddress = paymentInformation.getAddress();

            double price = MockDeliverySystem.getDeliveryPrice(Shop3000Information.ADDRESS, clientAddress);
            exchange.setProperty(DELIVERY_PRICE_PROPERTY, price);

        }
    }
}

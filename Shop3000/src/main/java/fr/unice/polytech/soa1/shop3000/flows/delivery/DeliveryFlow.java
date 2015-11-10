package fr.unice.polytech.soa1.shop3000.flows.delivery;

import fr.unice.polytech.soa1.shop3000.flows.pay.PayEndpoint;
import fr.unice.polytech.soa1.shop3000.flows.pay.ProceedPayment;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 11/10/2015.
 */
public class DeliveryFlow extends RouteBuilder {

    private MockedDeliverySystem mockedDeliverySystem = new MockedDeliverySystem();


    @Override
    public void configure() throws Exception {

        from(DeliveryEndpoints.GET_DELIVERY_PRICE.getInstruction())
                .log("Begin of the flow to get the delivery Price")
                .log("${property.paymentInformation}")

                        /** {@link MockedDeliverySystem#process(Exchange)}  **/
                .process(mockedDeliverySystem)
                .log("${property.deliveryPrice}")

                /** {@link ProceedPayment#configure()}  **/
                .to(PayEndpoint.SHOP3000_PAYMENT.getInstruction())
        ;

    }
}

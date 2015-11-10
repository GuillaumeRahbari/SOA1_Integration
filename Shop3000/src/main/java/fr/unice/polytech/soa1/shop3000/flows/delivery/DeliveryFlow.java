package fr.unice.polytech.soa1.shop3000.flows.delivery;

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
                .process(mockedDeliverySystem)
        ;

    }
}

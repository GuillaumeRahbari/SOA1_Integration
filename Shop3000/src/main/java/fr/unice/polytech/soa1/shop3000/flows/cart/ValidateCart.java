package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.flows.BooleanAndAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 */
public class ValidateCart extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(Endpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
                .log("body: ${body}")
                .multicast()
                    .aggregationStrategy(new BooleanAndAggregationStrategy())
                    .log("multicasting")
                    /*
                    .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction())
                    .to(Endpoint.ADD_TO_CART_BIKO.getInstruction())
                    .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction())
                    */
                    .log("merging")
                .end()
                .log("body: ${body}")
                .to(Endpoint.PAY.getInstruction());
    }
}

package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by guillaume on 02/11/2015.
 */
public class Unmarshaller extends RouteBuilder {

    private JsonUnmarshaller jsonUnmarshaller;

    public Unmarshaller () {
        jsonUnmarshaller = new JsonUnmarshaller();
    }

    @Override
    public void configure() throws Exception {

        /**
         * This route is here to unmarshall the json before go to the business layer.
         * We do this with the process JsonUnmarshaller.
         * It redirects to the ADD_ITEM_CART endpoint.
         */
        from(Endpoint.UNMARSHALL_JSON_ITEM.getInstruction())
                .log("DEbut du process")
                .process(jsonUnmarshaller)
                .to(Endpoint.ADD_ITEM_CART.getInstruction());

    }
}

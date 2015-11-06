package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/25/2015.
 */
public class CartFlows extends RouteBuilder {

    private CheckRequestStatus checkRequestStatus;

    public CartFlows() {
        this.checkRequestStatus = new CheckRequestStatus();
    }

    @Override
    public void configure() throws Exception {
        /** This flow start the flow to add an item to the cart of a mocked client added in the itemMock **/
        from(Endpoint.ADD_ITEM_CART.getInstruction())
                .log("${header.clientId}")
                .log("Begin add item to cart")
                .bean(AddItem.class, "addItemToCart(${header.clientId}, ${property.item})")
                .process(checkRequestStatus)

                /**
                 * {@link CartRoute}
                 */
                .to(Endpoint.CHECK_REQUEST_STATUS.getInstruction());
    }


}

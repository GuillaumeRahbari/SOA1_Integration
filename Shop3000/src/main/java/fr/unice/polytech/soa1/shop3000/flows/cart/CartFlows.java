package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.mock.ItemMock;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/25/2015.
 */
public class CartFlows extends RouteBuilder {

    private ItemMock itemMock;
    private AddItemToCart addItemToCart;

    public CartFlows() {
        this.itemMock = new ItemMock();
        this.addItemToCart = new AddItemToCart();
    }


    @Override
    public void configure() throws Exception {
            from(Endpoint.ADD_ITEM_CART.getInstruction())
                    .log("Begin add item to cart")
                    .process(itemMock)
                    .log("Client and item mocked in the exchange")
                    .process(addItemToCart)
                    .log("Item added to cart");

            from(Endpoint.VALIDATE_CART.getInstruction())
                    .log("Begin validate cart")
                    .choice()
                        .when();

            }
}

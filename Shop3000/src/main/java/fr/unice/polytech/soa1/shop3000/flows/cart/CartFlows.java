package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.flows.clientfile.CheckClientInDatabase;
import fr.unice.polytech.soa1.shop3000.mock.ItemMock;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/25/2015.
 */
public class CartFlows extends RouteBuilder {

    private ItemMock itemMock;
    private AddItemToCart addItemToCart;
    private CheckClientInDatabase checkClientInDatabase;
    private JsonToItem jsonToItem;
    private CheckRequestStatus checkRequestStatus;

    public CartFlows() {
        this.itemMock = new ItemMock();
        this.addItemToCart = new AddItemToCart();
        this.checkClientInDatabase = new CheckClientInDatabase();
        this.jsonToItem = new JsonToItem();
        this.checkRequestStatus = new CheckRequestStatus();
    }


    @Override
    public void configure() throws Exception {
        /** This flow start the flow to add an item to the cart of a mocked client added in the itemMock **/
        from(Endpoint.ADD_ITEM_CART.getInstruction())
                .log("${header.clientId}")
                .log("Begin add item to cart")
         //       .process(jsonToItem);
                .bean(AddItem.class, "addItemToCart(${header.clientId}, ${property.item})")
                .process(checkRequestStatus)
                .to(Endpoint.CHECK_REQUEST_STATUS.getInstruction());

/*                .process(itemMock)
                .process(checkClientInDatabase)
                *//* If the client is in the database then the item is added to the mocked cart otherwise an error is sent *//*
                .choice()
                    .when(simple("${property.client}"))
                        .log("Client already exist")
                        .log("Client and item mocked in the exchange")
                        .process(addItemToCart)
                        .log("Item added to cart")
                    .otherwise()
                        // Ici on change la response
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))


                .log("Error : The client doesn't exist");*/


    }


}

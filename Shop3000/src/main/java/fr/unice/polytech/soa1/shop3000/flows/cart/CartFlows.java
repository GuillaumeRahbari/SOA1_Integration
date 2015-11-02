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
    private CheckClientExistence checkClientExistenceBiko;
    private CheckClientExistenceBeer checkClientExistenceBeer;
    private CheckClientExistenceVolley checkClientExistenceVolley;
    private CheckClientInDatabase checkClientInDatabase;
    private JsonToItem jsonToItem;
    private CreateClientBiko createClientBiko;
    private CheckRequestStatus checkRequestStatus;

    public CartFlows() {
        this.itemMock = new ItemMock();
        this.addItemToCart = new AddItemToCart();
        this.checkClientExistenceBiko = new CheckClientExistence("biko");
        this.checkClientExistenceBeer = new CheckClientExistenceBeer();
        this.checkClientExistenceVolley = new CheckClientExistenceVolley();
        this.checkClientInDatabase = new CheckClientInDatabase();
        this.jsonToItem = new JsonToItem();
        this.createClientBiko = new CreateClientBiko();
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


        /**
         * This flow check if the client is already registered in the HailBeer system.
         * If the client is not registered then we create the client in the system.
         * And finally we add the item to the cart
         */
        from(Endpoint.CHECK_CLIENT_BEER.getInstruction())
                .log("Begin check client")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .setProperty("login",constant("test"))
                .setProperty("password", constant("test"))
                .recipientList(simple("http://localhost:8181/cxf/shop/account/${property.login}/${property.password}?bridgeEndpoint=true"))
                .process(checkClientExistenceBeer)
                .choice()
                    .when(simple("${property.result} == true"))
                    .when(simple("${property.result} == false"))
                        .to(Endpoint.CREATE_CLIENT_ALL_HAIL_BEER.getInstruction());

        /**
         * This flow check if the client is in the biko system, if not we create the client in the system and then
         * we add the items to the the cart.
         */
        from(Endpoint.CHECK_CLIENT_BIKO.getInstruction())
                .log("Begin check client")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .setProperty("clientID", constant("user1"))
                .recipientList(simple("http://localhost:8181/cxf/biko/clients/name/${property.clientID}?bridgeEndpoint=true"))
                .process(checkClientExistenceBiko)
                .choice()
                    .when(simple("${property.result} == true"))
                    .when(simple("${property.result} == false"))
                        .to(Endpoint.CREATE_CLIENT_BIKO.getInstruction());

        /**
         * This flow check if client is already registered in the volley system. Create the client otherwise.
         * Then add the items to the cart.
         */
        from(Endpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .log("Begin check client")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .setProperty("login", constant("jean"))
                .recipientList(simple("http://localhost:8181/cxf/volley/accounts/${property.login}?bridgeEndpoint=true"))
                .process(checkClientExistenceVolley)
                .choice()
                    .when(simple("${property.result} == true"))
                    .when(simple("${property.result} == false"))
                        .to(Endpoint.CREATE_CLIENT_VOLLEY_ON_THE_BEACH.getInstruction());


        from(Endpoint.CREATE_CLIENT_BIKO.getInstruction())
                .log("Begin create biko client")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant(""))
                .setProperty("username",constant("test"))
                .process(createClientBiko)
                .recipientList(simple("http://localhost:8181/cxf/biko/clients?bridgeEndpoint=true"));


    }


}

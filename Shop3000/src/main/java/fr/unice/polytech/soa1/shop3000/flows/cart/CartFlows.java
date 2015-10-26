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

    public CartFlows() {
        this.itemMock = new ItemMock();
        this.addItemToCart = new AddItemToCart();
        this.checkClientExistenceBiko = new CheckClientExistence("biko");
        this.checkClientExistenceBeer = new CheckClientExistenceBeer();
        this.checkClientExistenceVolley = new CheckClientExistenceVolley();
        this.checkClientInDatabase = new CheckClientInDatabase();
    }


    @Override
    public void configure() throws Exception {
        /** This flow start the flow to add an item to the cart of a mocked client added in the itemMock **/
        from(Endpoint.ADD_ITEM_CART.getInstruction())
                .log("Begin add item to cart")
                .process(itemMock)
                .process(checkClientInDatabase)
                /** If the client is in the database then the item is added to the mocked cart otherwise an error is sent **/
                .choice()
                    .when(simple("${property.client}"))
                        .log("Client already exist")
                        .log("Client and item mocked in the exchange")
                        .process(addItemToCart)
                        .log("Item added to cart")
                    .otherwise()
                        // TODO Ask mosser to send errors as 404.
                        .log("Error : The client doesn't exist");


            from(Endpoint.CHECK_CLIENT_BEER.getInstruction())
                    .log("Begin check client")
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .setBody(constant(""))
                    .to("http://localhost:8181/cxf/beers/account/{name}/{password}?bridgeEndpoint=true")
                    .process(checkClientExistenceBeer)
                    .choice()
                        .when(simple("${header.result} == true"))
                            .to(Endpoint.ADD_ITEM_CART.getInstruction())
                        .when(simple("${header.result} == false"))
                            .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction());

            from(Endpoint.CHECK_CLIENT_BIKO.getInstruction())
                    .log("Begin check client")
                    .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                    .setBody(constant(""))
                    .to("http://localhost:8181/cxf/biko/clients/name/{name}?bridgeEndpoint=true")
                    .process(checkClientExistenceBiko)
                    .choice()
                        .when(simple("${header.result} == true"))
                            .to(Endpoint.ADD_ITEM_CART.getInstruction())
                        .when(simple("${header.result} == false"))
                            .to(Endpoint.ADD_TO_CART_BIKO.getInstruction());

            from(Endpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                    .log("Begin check client")
                    .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                    .setBody(constant(""))
                    .to("http://localhost:8181/cxf/volley/accounts/{name}?bridgeEndpoint=true")
                    .process(checkClientExistenceVolley)
                    .choice()
                        .when(simple("${header.result} == true"))
                            .to(Endpoint.ADD_ITEM_CART.getInstruction())
                        .when(simple("${header.result} == false"))
                            .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction());
            }


}

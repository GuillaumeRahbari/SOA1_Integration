package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.flows.catalog.ReadResponseStream;
import fr.unice.polytech.soa1.shop3000.flows.clientfile.AddClientToDataBase;
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
    private ReadResponseStream readResponseStream;
    private CheckClientExistence checkClientExistence;

    public CartFlows() {
        this.itemMock = new ItemMock();
        this.addItemToCart = new AddItemToCart();
        this.checkClientExistence = new CheckClientExistence("biko");
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

            from(Endpoint.CHECK_CLIENT_BEER.getInstruction())
                    .log("Begin check client")
                    .process(checkClientExistence)
                    .choice()
                        .when(simple("${header.result} == true"))
                            .to(Endpoint.ADD_ITEM_CART.getInstruction())
                        .when(simple("${header.result} == false"))
                            .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction());

            from(Endpoint.CHECK_CLIENT_BIKO.getInstruction())
                    .log("Begin check client")
                    .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                    .setBody(constant(""))
                    .to("http://localhost:8181/cxf/biko/clients/name/user1?bridgeEndpoint=true")
                    .process(checkClientExistence)
                    .choice()
                        .when(simple("${header.result} == true"))
                            .to(Endpoint.ADD_ITEM_CART.getInstruction())
                        .when(simple("${header.result} == false"))
                            .to(Endpoint.ADD_TO_CART_BIKO.getInstruction());

            from(Endpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                    .log("Begin check client")
                    .process(checkClientExistence)
                    .choice()
                        .when(simple("${header.result} == true"))
                            .to(Endpoint.ADD_ITEM_CART.getInstruction())
                        .when(simple("${header.result} == false"))
                            .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction());
            }


}

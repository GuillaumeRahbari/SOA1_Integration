package fr.unice.polytech.soa1.shop3000.utils;

/**
 * @author Quentin Cornevin
 *
 * This interface will contain all the endpoint of the application
 */
public enum Endpoint {

    CLIENT_FILE_INPUT("direct:camel/input/client_file"),
    CLIENT_DATABASE("direct:checkClient"),
    GET_CATALOG("direct:get_catalog"),
    BIKO_CATALOG("direct:bikoCatalog"),
    VOLLEY_CATALOG("direct:volleyCatalog"),
    BEER_CATALOG("direct:beerCatalog"),
    ADD_ITEM_CART("direct:addItemCart"),
    VALIDATE_CART("direct:validateCart"),
    ADD_TO_CART_VOLLEY_ON_THE_BEACH("direct:addToCartVolleyOnTheBeach"),
    ADD_TO_CART_BIKO("direct:addToCartBiko"),
    ADD_TO_CART_ALL_HAIL_BEER("direct:addToCartAllHailBeer"),
    PAY("direct:pay"),
    CHECK_CLIENT_BEER("direct:checkClientBeer"),
    CHECK_CLIENT_BIKO("direct:checkClientBiko"),
    CHECK_CLIENT_VOLLEY("direct:checkClientVolley");

    private String instruction;

    private Endpoint(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

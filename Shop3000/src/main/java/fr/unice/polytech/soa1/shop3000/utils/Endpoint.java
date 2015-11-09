package fr.unice.polytech.soa1.shop3000.utils;

/**
 * @author Marc Karassev
 *
 * Enumeration of all the application's endpoints.
 */
public enum Endpoint {

    /** Client Endpoints **/
    CREATE_CLIENT_FILE("direct:createClientFile"),
    GET_CLIENT_FILE("direct:getClientFile"),
    DELETE_CLIENT_FILE("direct:deleteClientFile"),
    CLIENT_DATABASE("direct:checkClient"),
    CLIENT_UNMARSHALL("direct:unmarshallClient"),
    SEND_STATUS("direct:sendStatus"),


    /** Catalog Endpoints **/
    GET_CATALOG("direct:getCatalog"),
    BIKO_CATALOG("direct:bikoCatalog"),
    VOLLEY_CATALOG("direct:volleyCatalog"),
    BEER_CATALOG("direct:beerCatalog"),
    CSV_INPUT_DIRECTORY("file:csvInputDirectory"),

    /** Cart endpoints **/
    ADD_ITEM_CART("direct:addItemCart"),
    UNMARSHALL_JSON_ITEM("direct:unmarshallJsonItem"),
    ADD_TO_CART_VOLLEY_ON_THE_BEACH("direct:addToCartVolleyOnTheBeach"),
    ADD_TO_CART_BIKO("direct:addToCartBiko"),
    ADD_TO_CART_ALL_HAIL_BEER("direct:addToCartAllHailBeer"),
    CHECK_CLIENT_BEER("direct:checkClientBeer"),
    CHECK_CLIENT_BIKO("direct:checkClientBiko"),
    CHECK_CLIENT_VOLLEY("direct:checkClientVolley"),

    CREATE_CLIENT_VOLLEY_ON_THE_BEACH("direct:createClientVolley"),
    CREATE_CLIENT_BIKO("direct:createClientBiko"),
    CREATE_CLIENT_ALL_HAIL_BEER("direct:createClientBeer"),
    GET_CART("direct:getCart"),
    CHECK_REQUEST_STATUS("direct:checkRequestStatus"),
    /** Payment endpoints **/
    PAY("direct:pay");

    private String instruction;

    private Endpoint(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

package fr.unice.polytech.soa1.shop3000.flows.pay;

/**
 * @author Marc Karassev
 *
 * Endpoints of pay flows enumeration.
 */
public enum PayEndpoint {

    UNMARSHAL("direct:unmarshalPaymentData"),
    VALIDATE_PAYMENT_INFORMATION("direct:validatePaymentInformation"),
    EXTRACT_CART("direct:extractCart"),
    VALIDATE_CART("direct:validateCart"),
    BAD_PAYMENT_INFORMATION_ENDPOINT("direct:badPaymentInformation"),
    BAD_CLIENT_ID("direct:badClientID"),
    CHECK_CLIENT_BEER("direct:checkClientBeer"),
    CHECK_CLIENT_BIKO("direct:checkClientBiko"),
    CHECK_CLIENT_VOLLEY("direct:checkClientVolley"),
    UPDATE_BEST_SELLER("direct:updateBestSeller"),
    GET_CART_PRICE("direct:getCartPrice"),
    PAY("direct:pay");


    private String instruction;

    private PayEndpoint(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

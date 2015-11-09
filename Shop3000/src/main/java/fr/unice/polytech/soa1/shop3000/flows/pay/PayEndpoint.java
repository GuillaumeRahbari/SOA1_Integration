package fr.unice.polytech.soa1.shop3000.flows.pay;

/**
 * @author Marc Karassev
 *
 * Endpoints of pay flows enumeration.
 */
public enum PayEndpoint {

    UNMARSHAL("direct:unmarshalPaymentData"),
    VALIDATE_PAYMENT_INFORMATION("direct:validatePaymentInformation"),
    VALIDATE_CART("direct:validateCart"),
    BAD_PAYMENT_INFORMATION_ENDPOINT("direct:badPaymentInformation"),
    CHECK_CLIENT_BEER("direct:checkClientBeer"),
    CHECK_CLIENT_BIKO("direct:checkClientBiko"),
    CHECK_CLIENT_VOLLEY("direct:checkClientVolley"),
    UPDATE_BEST_SELLER("direct:updateBestSeller"),
    PAY("direct:pay");

    private String instruction;

    private PayEndpoint(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

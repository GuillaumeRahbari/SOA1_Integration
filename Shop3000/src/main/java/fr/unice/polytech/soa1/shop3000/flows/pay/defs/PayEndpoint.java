package fr.unice.polytech.soa1.shop3000.flows.pay.defs;

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
    SHOP3000_PAYMENT("direct:getCartPrice"),
    SHOPS_PAYMENT("activemq:payShops"),
    PAYMENT_TO_WS("direct:pay"),
    END_PAYMENT("direct:endPayment"),
    GET_DELIVERY_PRICE("direct:getDeliveryPrice"),
    PAY_BEER("activemq:payBeer"),
    PAY_BIKO("activemq:payBiko"),
    PAY_VOLLEY("activemq:payVolley");

    private String instruction;

    private PayEndpoint(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

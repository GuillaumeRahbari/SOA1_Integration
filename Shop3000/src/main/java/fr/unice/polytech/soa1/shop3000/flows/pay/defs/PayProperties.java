package fr.unice.polytech.soa1.shop3000.flows.pay.defs;

/**
 * Created by Quentin on 11/10/2015.
 */
public enum PayProperties {

    CART_PROPERTY("cart"),
    CART_PRICE_PROPERTY("cartPrice"),
    PAYMENT_STATE_PROPERTY("paymentStatus"),
    PAYMENT_INFORMATION_PROPERTY("paymentInformation"),
    CLIENT_ID_PROPERTY("clientID"),
    BAD_INFORMATION(""), //TODO a changer ?
    REQUEST_STATUS_PROPERTY("requestStatus"),
    DELIVERY_PRICE_PROPERTY("deliveryPrice");

    private String instruction;

    private PayProperties(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

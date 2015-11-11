package fr.unice.polytech.soa1.shop3000.flows.pay.defs;

/**
 * Created by Quentin on 11/10/2015.
 */
public enum ExchangeProperties {

    CART_PROPERTY("cart"), // TODO : peut etre a enlever
    CART_PRICE_PROPERTY("cartPrice"),
    PAYMENT_STATE_PROPERTY("paymentStatus"),
    PAYMENT_INFORMATION_PROPERTY("paymentInformation"),
    CLIENT_ID_PROPERTY("clientID"),
    BAD_INFORMATION(""), //TODO a changer ?
    REQUEST_STATUS_PROPERTY("requestStatus")

    ;

    private String instruction;

    private ExchangeProperties(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

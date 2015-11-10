package fr.unice.polytech.soa1.shop3000.flows.pay;

/**
 * Created by Quentin on 11/10/2015.
 */
public enum ExchangeProperties {

    CART_PROPERTY("cart"),
    CART_PRICE_PROPERTY("cartPrice"),
    PAYMENT_STATE_PROPERTY("paymentStatus");



    private String instruction;

    private ExchangeProperties(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

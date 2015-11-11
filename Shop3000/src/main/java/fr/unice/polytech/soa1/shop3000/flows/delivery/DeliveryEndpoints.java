package fr.unice.polytech.soa1.shop3000.flows.delivery;

/**
 * Created by Quentin on 11/10/2015.
 */
public enum DeliveryEndpoints {

    GET_DELIVERY_PRICE("direct:getDeliveryPrice");

    private String instruction;

    private DeliveryEndpoints(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

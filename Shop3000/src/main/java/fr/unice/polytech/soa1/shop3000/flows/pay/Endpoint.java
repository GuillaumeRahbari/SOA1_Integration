package fr.unice.polytech.soa1.shop3000.flows.pay;

/**
 * @author Marc Karassev
 *
 * Endpoints of pay flows enumeration.
 */
public enum Endpoint {

    VALIDATE_CART("direct:validateCart"),
    PAY("direct:pay");

    private String instruction;

    private Endpoint(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}
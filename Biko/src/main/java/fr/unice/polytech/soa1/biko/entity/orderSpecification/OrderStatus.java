package fr.unice.polytech.soa1.biko.entity.orderSpecification;

/**
 * Created by Quentin on 10/4/2015.
 */
public enum OrderStatus {

    AWAITING_PAYMENT,
    IN_PREPARATION,
    DELIVERING,
    DELIVERED;

/*
    private static Map<String, OrderStatus> namesMap = new HashMap<>();

    static  {
        namesMap.put("Awaiting payment", AWAITING_PAYMENT);
        namesMap.put("In preparation", IN_PREPARATION);
        namesMap.put("Delivering", DELIVERING);
        namesMap.put("Delivered", DELIVERED);
    }

    @JsonCreator
    public static OrderStatus forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for(Map.Entry<String, OrderStatus> entry : namesMap.entrySet()) {
            if(entry.getValue() == this) {
                return entry.getKey();
            }
        }
        return null;
    }
*/

}

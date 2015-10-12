package fr.unice.polytech.soa1.biko.entity.orderSpecification;

/**
 * Created by Quentin on 10/5/2015.
 */
public enum ShippingMode {

    NORMAL,
    EXPRESS,
    ONE_DAY;

/*    private static Map<String, Shipping> namesMap = new HashMap<>();

    static {
        namesMap.put("normal", NORMAL);
        namesMap.put("express", EXPRESS);
        namesMap.put("one_day", ONE_DAY);
    }

    @JsonCreator
    public static Shipping forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for(Map.Entry<String, Shipping> entry : namesMap.entrySet()) {
            if(entry.getValue() == this) {
                return entry.getKey();
            }
        }
        return null;
    }*/

}

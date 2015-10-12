package fr.unice.polytech.soa1.biko.entity.payment;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 05/10/2015.
 */
public enum PaymentStatus {
    TODO,
    FAILED,
    SUCCEEDED;

    private static Map<String, PaymentStatus> namesMap = new HashMap<>();

    static  {
        namesMap.put("Awaiting validation", TODO);
        namesMap.put("Payment failed", FAILED);
        namesMap.put("Payment succeeded", SUCCEEDED);
    }

    @JsonCreator
    public static PaymentStatus forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for(Map.Entry<String, PaymentStatus> entry : namesMap.entrySet()) {
            if(entry.getValue() == this) {
                return entry.getKey();
            }
        }
        return null;
    }
}

package fr.unice.polytech.soa1.biko.entity.stuff;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nabil on 04/10/2015.
 */
public enum Type {
    HANDLEBAR,WHEEL;

    private static Map<String, Type> typesMap = new HashMap<>();

    static {
        typesMap.put("handlebar", HANDLEBAR);
        typesMap.put("wheel", WHEEL);
    }

    @JsonCreator
    public static Type forValue(String value) {
        return typesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for(Map.Entry<String, Type> entry : typesMap.entrySet()) {
            if(entry.getValue() == this) {
                return entry.getKey();
            }
        }
        return null;
    }
}

package fr.unice.polytech.soa1.shop3000.business.customization;

import fr.unice.polytech.soa1.shop3000.business.customization.Customization;

import java.util.List;

/**
 * @author : Laureen Ginier
 *
 * This class represents the customization of an item of the Biko shop.
 */
public class BikoCustom extends Customization {

    private List<String> components;

    public BikoCustom(List<String> components){
        this.components = components;
    }

    public String toString(){
        String s = "{\"components\": {";
        s += "\"name\":\"" + components.get(0) + "\"";
        for(int i = 1; i < components.size(); i++) {
            s += ", \"name\":\"" + components.get(i) + "\"";
        }
        s += "}";
        return s;
    }

}

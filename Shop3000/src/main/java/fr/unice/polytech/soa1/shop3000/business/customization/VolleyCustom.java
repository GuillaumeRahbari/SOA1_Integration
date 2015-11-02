package fr.unice.polytech.soa1.shop3000.business.customization;

/**
 * @author: Laureen Ginier
 *
 * This class represents the customization of an item of the VolleyOnTheBeach shop.
 */
public class VolleyCustom extends Customization {

    private String color;

    public VolleyCustom(String color){
        this.color = color;
    }

    public String toString(){
        return "{\"color\":\""+color+"\"}";
    }

}

package fr.unice.polytech.soa1.shop3000.business.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : Laureen Ginier
 *
 * This class represents the catalog of an item of the Biko shop.
 */
public class Customization {

    private String name;
    private double price;
    private String type;

    @JsonCreator
    public Customization(@JsonProperty(value = "name", required = true) String name,
                         @JsonProperty(value = "price", required = true) double price,
                         @JsonProperty(value = "type", required = true) String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

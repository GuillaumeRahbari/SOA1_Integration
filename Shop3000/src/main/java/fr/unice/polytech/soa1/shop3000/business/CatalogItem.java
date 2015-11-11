package fr.unice.polytech.soa1.shop3000.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: Laureen Ginier on 21/10/15.
 *
 * This class represents an item of the shop3000 catalog (which is an aggregation of the other catalogs).
 */
public class CatalogItem {

    private String name;
    private double price;
    private String description;

    public CatalogItem(){
    };

    @JsonCreator
    public CatalogItem(@JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "price", required = true) double price){
        this.name = name;
        this.price = price;
    }

    /**
     * Constructor of a shop3000 catalog
     * @param name name of the item
     * @param price price of the item
     * @param description description of the item
     */
    public CatalogItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the json string representing the CatalogItem.
     * @return valid json string
     */
    public String toJsonString() {
        String s = "{\"name\":\"" + name
                + "\", \"price\":" + price
                + ", \"description\":\"" + description + "\"}";
        return s;
    }

    @Override
    public boolean equals (Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof CatalogItem)) {
            return false;
        }
        CatalogItem otherCatalogItem = (CatalogItem) other;
        if (this.name.equals(otherCatalogItem.getName())
                && this.description.equals(otherCatalogItem.getDescription())
                && this.price == otherCatalogItem.getPrice()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + description.hashCode();
        return result;
    }

}

package fr.unice.polytech.soa1.shop3000.business;

/**
 * @author: Laureen Ginier on 21/10/15.
 *
 * This class represents an item of the shop3000 catalog (which is an aggregation of the other catalogs).
 */
public class CatalogItem {

    private String name;
    private double price;
    private String description;

    public CatalogItem(String name, double price){
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

}

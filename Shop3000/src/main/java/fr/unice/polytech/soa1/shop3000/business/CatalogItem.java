package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.business.customization.Customization;

/**
 * @author: Laureen Ginier on 21/10/15.
 *
 * This class represents an item of the shop3000 catalog (which is an aggregation of the other catalogs).
 */
public class CatalogItem {

    private String name;
    private double price;
    private String description;
    private Customization customization;

    public CatalogItem(String name, double price){
        this.name = name;
        this.price = price;
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

    public Customization getCustomization() {
        return customization;
    }

    public void setCustomization(Customization customization) {
        this.customization = customization;
    }

}

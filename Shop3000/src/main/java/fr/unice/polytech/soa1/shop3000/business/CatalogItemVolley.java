package fr.unice.polytech.soa1.shop3000.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Laureen Ginier
 */
public class CatalogItemVolley extends CatalogItem {

    /**
     * Constructor for CatalogItem from VolleyOnTheBeach catalog
     * @param name name of the item
     * @param price price of the item
     * @param description description of the item
     * @param color customization of the item
     */
    @JsonCreator
    public CatalogItemVolley(@JsonProperty(value = "name", required = true) String name,
                             @JsonProperty(value = "price", required = true) double price,
                             @JsonProperty(value = "description", required = true) String description,
                             @JsonProperty(value = "color", required = false) String color){
        super(name, price, description + " color: " + color);
    }

}

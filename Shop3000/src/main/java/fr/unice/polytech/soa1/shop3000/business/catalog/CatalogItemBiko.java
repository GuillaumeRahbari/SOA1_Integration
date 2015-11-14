package fr.unice.polytech.soa1.shop3000.business.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Laureen Ginier
 */
public class CatalogItemBiko extends CatalogItem {

    /**
     * Constructor for CatalogItem from Biko shop
     * @param price price of the item
     * @param name name of the item
     * @param color description of the item
     * @param id id of the item in the Biko shop
     */
    @JsonCreator
    public CatalogItemBiko(@JsonProperty(value = "price", required = true) double price,
                       @JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "color", required = true) String color,
                       @JsonProperty(value = "id", required = true) int id) {
        super(name, price, new ItemDescription(id,color), null);
    }
}

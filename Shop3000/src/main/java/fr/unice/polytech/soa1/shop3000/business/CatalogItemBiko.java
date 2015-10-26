package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.business.customization.Customization;

/**
 * @author Laureen Ginier
 */
public class CatalogItemBiko extends CatalogItem {
    public CatalogItemBiko(String name, double price, String description, Customization customization) {
        super(name, price, description, customization);
    }

    /*@JsonCreator
    public CatalogItemBiko(@JsonProperty(value="name", required = true) String name,
                           @JsonProperty(value="price", required = true) double price,
                           @JsonProperty(value="color", required = true) String description,
                           Customization customization) {
        super(name, price, "", customization);
    }*/
}

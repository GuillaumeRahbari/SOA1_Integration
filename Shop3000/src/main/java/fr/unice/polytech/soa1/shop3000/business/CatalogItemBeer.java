package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.business.customization.Customization;

/**
 * @author Laureen Ginier
 */
public class CatalogItemBeer extends CatalogItem {
    public CatalogItemBeer(String name, double price, String description) {
        super(name, price, description);
    }

    /*@JsonCreator
    public CatalogItemBeer(@JsonProperty(value = "name", required = true) String name,
                           @JsonProperty(value = "price", required = true) double price,
                           @JsonProperty(value = "description", required = true) String description,
                           @JsonProperty(value = "color", required = false) Customization customization) {
        super(name, price, "", customization);
    }*/

}

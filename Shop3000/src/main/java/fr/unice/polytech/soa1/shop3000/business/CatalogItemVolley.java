package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.business.customization.Customization;

/**
 * @author Laureen Ginier
 */
public class CatalogItemVolley extends CatalogItem {
    public CatalogItemVolley(String name, double price, String description, Customization customization) {
        super(name, price, description, customization);
    }

    /*@JsonCreator
    public CatalogItemVolley(@JsonProperty(value = "name", required = true) String name,
                             @JsonProperty(value = "price", required = true) double price,
                             @JsonProperty(value = "description", required = true) String description,
                             @JsonProperty(value = "color", required = false) Customization customization) {
        super(name, price, "", customization);
    }*/

}

package fr.unice.polytech.soa1.shop3000.business.catalog;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

/**
 * @author: Laureen Ginier on 21/10/15.
 *
 * This class represents an item of the shop3000 catalog (which is an aggregation of the other catalogs).
 */
public class CatalogItem {

    protected String name;
    protected double price;
    protected ItemDescription description;
    protected List<Customization> customization;

    public CatalogItem(){
    };

    @JsonCreator
    public CatalogItem(@JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "price", required = true) double price,
                       @JsonProperty(value = "description", required = true) ItemDescription description,
                       @JsonProperty(value = "catalog", required = false) List<Customization> custom) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.customization = custom;
    }

    public CatalogItem(String name, double quantity) {
        this.name = name;
        this.description = new ItemDescription(quantity);
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

    @JsonIgnore
    public List<Customization> getCustomization() {
        return customization;
    }

    public void setCustomization(List<Customization> customization) {
        this.customization = customization;
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

/*    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + description.hashCode();
        return result;
    }*/

    //@JsonIgnore
    public ItemDescription getDescription() {
        return description;
    }

    public void setDescription(ItemDescription description) {
        this.description = description;
    }
}

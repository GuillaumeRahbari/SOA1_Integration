package fr.unice.polytech.soa1.shop3000.business.customization;

/**
 * @author: Laureen Ginier
 *
 * This class represents the customization of an item of the AllHailBeer shop.
 */
public class BeerCustom extends Customization {

    private boolean isClientCustomization;

    public BeerCustom(boolean isClientCustomization){
        this.isClientCustomization = isClientCustomization;
    }

}

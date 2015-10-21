package fr.unice.polytech.soa1.shop3000.business.customization;

/**
 * @author: Laureen Ginier
 *
 * This class represents the customization of an item of the AllHailBeer shop.
 */
public class BeerCustom extends Customization {

    private boolean clientCustomization;

    public BeerCustom(boolean isClientCustomization){
        this.clientCustomization = isClientCustomization;
    }

}

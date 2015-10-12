package fr.unice.polytech.soa1.biko.entity.orderSpecification;

/**
 * Created by Quentin on 10/5/2015.
 */
public class ShippingWrapper {

    ShippingMode shippingMode;

    public ShippingWrapper(ShippingMode shippingMode) {
        this.shippingMode = shippingMode;
    }

    public ShippingWrapper() {
        this(ShippingMode.NORMAL);
    }

    public ShippingMode getShipping() {
        return shippingMode;
    }

    public void setShipping(ShippingMode shipping) {
        this.shippingMode = shipping;
    }
}

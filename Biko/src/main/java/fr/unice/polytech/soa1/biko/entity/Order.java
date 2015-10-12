package fr.unice.polytech.soa1.biko.entity;

import fr.unice.polytech.soa1.biko.entity.orderSpecification.Address;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.OrderStatusWrapper;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.ShippingWrapper;

import java.util.List;

/**
 * Created by Nabil on 30/09/2015.
 */
public class Order {

    private long id;
    private List<ConnectedBike> bikes;
    private Address address;
    private OrderStatusWrapper orderStatus;
    private long tracking;
    private ShippingWrapper shipping;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order(List<ConnectedBike> bikes) {
        this.bikes = bikes;
        this.orderStatus = new OrderStatusWrapper();
        this.address = new Address();
        this.shipping = new ShippingWrapper();
    }

    public Order(List<ConnectedBike> bikes, Address address, ShippingWrapper shipping) {
        this.bikes = bikes;
        this.orderStatus = new OrderStatusWrapper();
        this.address = address;
        this.shipping = shipping;
    }


    public List<ConnectedBike> getBikes() {
        return bikes;
    }

    public void setBikes(List<ConnectedBike> bikes) {
        this.bikes = bikes;
    }

    public Address getAdress() {
        return address;
    }

    public void setAdress(Address address) {
        this.address = address;
    }

    public OrderStatusWrapper getStatus() {
        return orderStatus;
    }

    public void setStatus(OrderStatusWrapper status) {
        this.orderStatus = status;
    }

    public long getTracking() {
        return tracking;
    }

    public void setTracking(long tracking) {
        this.tracking = tracking;
    }

    public ShippingWrapper getShipping() {
        return shipping;
    }

    public void setShipping(ShippingWrapper shipping) {
        this.shipping = shipping;
    }
}

package fr.unice.polytech.soa1.biko.entity;

import fr.unice.polytech.soa1.biko.entity.orderSpecification.Address;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.ShippingMode;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.ShippingWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nabil on 30/09/2015.
 */
public class Cart {

    private List<ConnectedBike> items;
    private int price;
    private ShippingWrapper shipping;
    private Address address;

    public List<ConnectedBike> getItems() {
        return items;
    }

    public void setItems(List<ConnectedBike> items) {
        this.items = items;
    }

    public Cart(List<ConnectedBike> items) {
        this.items = items;
    }

    public Cart() {
        this.items = new ArrayList<>();
        this.price = 0;
        this.address = new Address();
        this.shipping = new ShippingWrapper(ShippingMode.NORMAL);
    }

    public Cart(ConnectedBike item){
        items = new ArrayList<>();
        items.add(item);
        price = item.getPrice();
        this.address = new Address();
        this.shipping = new ShippingWrapper(ShippingMode.NORMAL);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void addItem(ConnectedBike item){
        items.add(item);
        price += item.getPrice();
    }

    public void setShipping(ShippingWrapper shipping) {
        this.shipping = shipping;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ShippingWrapper getShipping() {
        return shipping;
    }

    public Address getAddress() {
        return address;
    }
}

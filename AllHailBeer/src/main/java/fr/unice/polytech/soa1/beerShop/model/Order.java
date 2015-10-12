package fr.unice.polytech.soa1.beerShop.model;

/**
 * Created by tom on 06/10/15.
 */
public class Order {
    private Cart cart;
    private Long creditCard;
    private Long id;
    private Float price;

    public Order() {
    }

    public Order(Cart cart, Long creditCard, Long id) {
        this.cart = cart;
        this.creditCard = creditCard;
        this.id = id;
    }

    public Order(Cart cart, String creditCard) {
        this.cart = cart;
        this.creditCard = Long.valueOf(creditCard);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Long creditCard) {
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}

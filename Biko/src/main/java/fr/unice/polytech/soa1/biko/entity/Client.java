package fr.unice.polytech.soa1.biko.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This class represent the client in the data base
 */
public class Client {

    private long id;
    private String name;
    private Cart cart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Create a client with a given id and a given name.
     *
     * @param id of the client created
     * @param name of the client created
     */
    public Client(long id, String name) {
        this.id = id;
        this.name = name;
        this.cart = new Cart();
    }

    /**
     * Create a default client with 0 as id and default as name;
     */
    public Client() {
        this(0,"default");
    }

    @JsonIgnore
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

package fr.unice.polytech.soa1.biko.entity;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This class represent the bike
 */
public class ConnectedBike {

    private String name;
    private String color;
    private long id;
    private int price;

    /**
     * This contructor allow us to create a ConnectedBike with a given name, color and id
     * @param name
     * @param color
     * @param id
     */
    public ConnectedBike(String name, String color, long id, int price) {
        this.color = color;
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     * Create a default bike with "defaultBike" as name "defaultColor" as color and 0 as id
     */
    public ConnectedBike() {
        this("defaultBike", "defaultColor",0,0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

package fr.unice.polytech.soa1.biko.entity.stuff;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * Abstract class for all the different componenet of a bike.
 */
public class Stuff {

    private String name;
    private int price;
    private Type type;

    public Stuff(String name, int price, Type type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Stuff() {
        this("default",0, Type.HANDLEBAR);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

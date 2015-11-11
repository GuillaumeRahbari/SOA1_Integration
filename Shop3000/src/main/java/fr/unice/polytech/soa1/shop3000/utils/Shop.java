package fr.unice.polytech.soa1.shop3000.utils;

/**
 * @author Laureen Ginier
 */
public enum Shop {

    BIKO("biko"),
    BEER("allhailbeer"),
    VOLLEY("volleyonthebeach");

    private String name;

    private Shop(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}

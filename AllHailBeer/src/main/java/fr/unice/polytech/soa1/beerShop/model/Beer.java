package fr.unice.polytech.soa1.beerShop.model;

/**
 * Created by tom on 28/09/15.
 */
public class Beer {
    private String name;
    private String user;
    private String cereale;
    private String titration;
    private String gout;
    private Float pricePerLiter;

    public Beer(){

    }

    public Beer(String name, String user) {
        this.name = name;
        this.user = user;
    }

    public String getCereale() {
        return cereale;
    }

    public void setCereale(String cereale) {
        this.cereale = cereale;
    }

    public String getTitration() {
        return titration;
    }

    public void setTitration(String titration) {
        this.titration = titration;
    }

    public String getGout() {
        return gout;
    }

    public void setGout(String gout) {
        this.gout = gout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Float getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(Float pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }
}

package fr.unice.polytech.soa1.shop3000.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Quentin on 11/11/2015.
 */
public class ItemDescription {

    private int idBiko;
    private String color;
    private String titration;
    private String gout;
    private String cereale;

    public ItemDescription(int idBiko, String color) {
        this.idBiko = idBiko;
        this.color = color;
    }

    public ItemDescription(String color) {
        this.color = color;
    }

    public ItemDescription(String titration, String gout, String cereale) {
        this.cereale = cereale;
        this.titration = titration;
        this.gout = gout;
    }

    @JsonCreator
    public ItemDescription(@JsonProperty(value = "id") int idBiko,@JsonProperty(value = "color") String color,@JsonProperty(value = "titration") String titration,
                           @JsonProperty(value = "gout") String gout,@JsonProperty(value = "cereale") String cereale) {
        this.color = color;
        this.idBiko = idBiko;
        this.titration = titration;
        this.gout = gout;
        this.cereale = cereale;
    }

    public int getIdBiko() {
        return idBiko;
    }

    public void setIdBiko(int idBiko) {
        this.idBiko = idBiko;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getCereale() {
        return cereale;
    }

    public void setCereale(String cereale) {
        this.cereale = cereale;
    }
}

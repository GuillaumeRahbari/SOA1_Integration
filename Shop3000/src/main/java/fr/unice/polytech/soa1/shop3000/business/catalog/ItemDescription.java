package fr.unice.polytech.soa1.shop3000.business.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Quentin on 11/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemDescription {

    private int idBiko;
    private String color;
    private String titration;
    private String gout;
    private String cereale;
    private double quantite;

    public ItemDescription(int idBiko, String color) {
        this.idBiko = idBiko;
        this.color = color;
    }

    public ItemDescription(String color) {
        this.color = color;
    }

    public ItemDescription(double quantity) {
        this.quantite = quantity;
    }

    public ItemDescription(String titration, String gout, String cereale) {
        this.cereale = cereale;
        this.titration = titration;
        this.gout = gout;
    }

    @JsonCreator
    public ItemDescription(@JsonProperty(value = "id") int idBiko,
                           @JsonProperty(value = "color") String color,
                           @JsonProperty(value = "titration") String titration,
                           @JsonProperty(value = "gout") String gout,
                           @JsonProperty(value = "cereale") String cereale,
                           @JsonProperty(value = "quantite") double quantity) {
        this.color = color;
        this.idBiko = idBiko;
        this.titration = titration;
        this.gout = gout;
        this.cereale = cereale;
        this.quantite = quantity;
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

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String toJsonString() {
        String s = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            s = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemDescription that = (ItemDescription) o;

        if (idBiko != that.idBiko) return false;
        if (Double.compare(that.quantite, quantite) != 0) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (titration != null ? !titration.equals(that.titration) : that.titration != null) return false;
        if (gout != null ? !gout.equals(that.gout) : that.gout != null) return false;
        return !(cereale != null ? !cereale.equals(that.cereale) : that.cereale != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idBiko;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (titration != null ? titration.hashCode() : 0);
        result = 31 * result + (gout != null ? gout.hashCode() : 0);
        result = 31 * result + (cereale != null ? cereale.hashCode() : 0);
        temp = Double.doubleToLongBits(quantite);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

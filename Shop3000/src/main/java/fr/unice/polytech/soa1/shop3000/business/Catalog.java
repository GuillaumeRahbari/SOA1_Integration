package fr.unice.polytech.soa1.shop3000.business;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Laureen Ginier
 * This class represents the aggregation of all the various catalogs shops into a unique catalog
 */
public class Catalog {

    private static Catalog instance = new Catalog();

    private List<CatalogItem> itemsBiko;
    private List<CatalogItem> itemsBeer;
    private List<CatalogItem> itemsVolley;

    private Catalog() {
        this.itemsBiko = new ArrayList<CatalogItem>();
        this.itemsBeer = new ArrayList<CatalogItem>();
        this.itemsVolley = new ArrayList<CatalogItem>();
    }

    public static Catalog getInstance(){
        return instance;
    }

    public List<CatalogItem> getItemsBiko() {
        return itemsBiko;
    }

    public void setItemsBiko(List<CatalogItem> itemsBiko) {
        this.itemsBiko = itemsBiko;
    }

    public List<CatalogItem> getItemsBeer() {
        return itemsBeer;
    }

    public void setItemsBeer(List<CatalogItem> itemsBeer) {
        this.itemsBeer = itemsBeer;
    }

    public List<CatalogItem> getItemsVolley() {
        return itemsVolley;
    }

    public void setItemsVolley(List<CatalogItem> itemsVolley) {
        this.itemsVolley = itemsVolley;
    }

    public List<CatalogItem> getAll() {
        List<CatalogItem> fullList = this.itemsBeer;
        fullList.addAll(this.itemsBiko);
        fullList.addAll(this.itemsVolley);
        return fullList;
    }
}

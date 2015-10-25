package fr.unice.polytech.soa1.shop3000.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 10/25/2015.
 */
public class Cart {

    private List<CatalogItem> catalogItemList;

    public Cart(List<CatalogItem> catalogItems) {
        this.catalogItemList = catalogItems;
    }

    public Cart() {
        this.catalogItemList = new ArrayList<CatalogItem>();
    }

    public List<CatalogItem> getCatalogItemList() {
        return catalogItemList;
    }

    public void setCatalogItemList(List<CatalogItem> catalogItemList) {
        this.catalogItemList = catalogItemList;
    }
}

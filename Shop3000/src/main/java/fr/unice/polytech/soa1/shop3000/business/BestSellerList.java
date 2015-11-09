package fr.unice.polytech.soa1.shop3000.business;

import java.util.HashMap;

/**
 * Created by Quentin on 11/9/2015.
 */
public class BestSellerList {

    private HashMap<CatalogItem, Integer> bestSellerList;

    public BestSellerList() {
        this.bestSellerList = new HashMap<CatalogItem, Integer>();
    }

    public HashMap<CatalogItem, Integer> getBestSellerList() {
        return bestSellerList;
    }

    public CatalogItem getBestSeller() {
        return null;
    }


    public void setBestSellerList(HashMap<CatalogItem, Integer> bestSellerList) {
        this.bestSellerList = bestSellerList;
    }
}

package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.business.catalog.CatalogItem;

import java.util.HashMap;

/**
 * Created by Quentin on 11/9/2015.
 */
public class BestSellerList {

    private static HashMap<CatalogItem, Integer> bestSellerList = new HashMap<>();

    public static HashMap<CatalogItem, Integer> getBestSellerList() {
        return bestSellerList;
    }

    public CatalogItem getBestSeller() {
        return null;
    }

    public static void addItemToList (CatalogItem catalogItem) {
        bestSellerList.put(catalogItem, 1);
    }


    public void setBestSellerList(HashMap<CatalogItem, Integer> bestSellerList) {
        this.bestSellerList = bestSellerList;
    }
}

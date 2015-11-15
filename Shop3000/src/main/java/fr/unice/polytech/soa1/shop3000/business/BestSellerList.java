package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.business.catalog.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.catalog.ItemDescription;

import java.util.HashMap;

/**
 * Created by Quentin on 11/9/2015. {"name":"net","price":9.5,"description":{"color":"BLUE"}}
 */
public class BestSellerList {

    private static final CatalogItem INITIAL_BEST_SELLER = new CatalogItem("net", 9.5, new ItemDescription("BLUE"), null);

    private static HashMap<CatalogItem, Integer> bestSellerList;

    public static HashMap<CatalogItem, Integer> getBestSellerList() {
        return bestSellerList;
    }

    public static void addItemToList (CatalogItem catalogItem) {
        bestSellerList.put(catalogItem, 1);
    }

    static {
        bestSellerList = new HashMap<>();
        addItemToList(INITIAL_BEST_SELLER);
    }
}

package fr.unice.polytech.soa1.shop3000.business;

import fr.unice.polytech.soa1.shop3000.utils.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Quentin on 10/25/2015.
 * Updated by Laureen on 11/10/2015.
 *
 */
public class Cart extends HashMap<String, List<CatalogItem>> {

    public Cart() {
        super();
        put(Shop.BEER.getName(), new ArrayList<CatalogItem>());
        put(Shop.BIKO.getName(), new ArrayList<CatalogItem>());
        put(Shop.VOLLEY.getName(), new ArrayList<CatalogItem>());
    }
}

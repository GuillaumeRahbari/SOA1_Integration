package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.BestSellerList;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by guillaume on 11/11/2015.
 */
public class BestSellerBean {

    /**
     * This method gets the best seller.
     * @return The best seller
     */
    public String getBestSeller (){
        // The best seller
        CatalogItem bestSeller = null;
        // A temporary int to know which is the best seller.
        int tmp = 0;
        // A interator over the map
        Iterator it = BestSellerList.getBestSellerList().entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            // Of it's superior we change the best seller
            if ((int)pair.getValue() > tmp) {
                tmp = (int)pair.getValue();
                bestSeller = (CatalogItem)pair.getKey();
            }
        }
        return bestSeller.toJsonString();
    }
}

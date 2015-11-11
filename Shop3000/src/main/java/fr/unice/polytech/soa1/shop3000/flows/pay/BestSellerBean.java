package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guillaume on 10/11/2015.
 */
public class BestSellerBean {

    /**
     * This method updates the best seller.
     *
     * @param clientFirstName The client's first name.
     */
    public void updateBestSeller (String clientFirstName){
        // We get the client
        Client client = ClientStorage.read(clientFirstName);
        // We get the client's cart
        Cart clientCart = client.getCart();
        // We create an iterator over the client's cart hashmap
        Iterator it = clientCart.entrySet().iterator();
        while (it.hasNext()){
            // We get the pair (key, value)
            Map.Entry pair = (Map.Entry)it.next();
            // On parcourt la partie value qui est une arraylist
            for (CatalogItem catalogItem : (ArrayList<CatalogItem>)pair.getValue()) {

                // If it exists in the list.
                if (BestSellerList.getBestSellerList().containsKey(catalogItem)) {
                    BestSellerList.getBestSellerList().put(catalogItem, BestSellerList.getBestSellerList().get(catalogItem) + 1);
                }
                // If not
                else {
                    BestSellerList.addItemToList(catalogItem);
                }
            }
          //  it.remove(); // avoids a ConcurrentModificationException
        }
    }

}

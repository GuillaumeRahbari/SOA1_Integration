package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.business.catalog.Catalog;
import fr.unice.polytech.soa1.shop3000.business.catalog.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;

import java.util.ArrayList;

/**
 * Created by Quentin on 11/1/2015.
 * Updated by Laureen on 11/10/2015.
 */
public class AddItem {

    /**
     * This function is the bean that we call to add an item to a cart.
     *
     * @param clientFirstName A string containing the client first name.
     * @param catalogItem A CatalogItem containing the items we want to add.
     * @return A boolean. It returns :
     * <ul>
     *     <li>True when the client exists. In this case, it also adds the items in client cart.</li>
     *     <li>False when the client doesn't exist.</li>
     * </ul>
     */
    public boolean addItemToCart(String clientFirstName, CatalogItem catalogItem){
        Client client = ClientStorage.read(clientFirstName);
        // If the client exists.
        if (client != null){
            return addItemToCart(client,catalogItem);
        }
        return false;
    }

    /**
     * This function is really the one adding a CatalogItem into the client cart.
     *
     * @param client The client we want to add items in his cart.
     * @param catalogItem The items we want to add in the client cart.
     */
    private boolean addItemToCart (Client client, CatalogItem catalogItem) {
        String shopName = Catalog.getInstance().getShopName(catalogItem.getName());
        if(shopName != null && !("".equals(shopName))) {
            if (client.getCart().get(shopName) == null) {
                client.getCart().put(shopName, new ArrayList<CatalogItem>());
            }
            client.getCart().get(shopName).add(catalogItem);
            return true;
        }
        return false;
    }
}

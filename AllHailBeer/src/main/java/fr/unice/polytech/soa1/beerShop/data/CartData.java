package fr.unice.polytech.soa1.beerShop.data;

import fr.unice.polytech.soa1.beerShop.model.Beer;
import fr.unice.polytech.soa1.beerShop.model.Cart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tom on 05/10/15.
 */
public class CartData {
    private static Map<String, Cart> data = DaoUtils.readData("cartData.json", String.class, Cart.class);

    public static Cart get(String userId){
        return data.get(userId);
    }

    public static void add(Cart cart){
        data.put(cart.getOwner(),cart);
    }

    public static void update(Cart cart){
        delete(cart);
        data.put(cart.getOwner(), cart);
    }

    public static void delete(Cart cart){
        data.remove(cart.getOwner());
    }

    public static Map<String, Cart> getData() {
        return data;
    }
}

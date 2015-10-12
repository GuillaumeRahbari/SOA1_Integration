package fr.unice.polytech.soa1.beerShop.data;

import fr.unice.polytech.soa1.beerShop.model.Beer;
import fr.unice.polytech.soa1.beerShop.model.Cart;
import fr.unice.polytech.soa1.beerShop.model.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tom on 06/10/15.
 */
public class OrderData {
    private static Map<Long, Order> data = DaoUtils.readData("orderData.json", Long.class, Order.class);
    private static Long currentId=new Long(data.size());

    public static Order get(Long orderId){
        return data.get(orderId);
    }

    public static Long add(Order order){
        Float totalPrice = new Float(0.0);
        for (Map.Entry<String,Float> entry : order.getCart().getCartData().entrySet()){
            totalPrice += BeerData.get(entry.getKey()).getPricePerLiter()*entry.getValue();
        }

        order.setId(++currentId);
        order.setPrice(totalPrice);

        data.put(order.getId(), order);

        return currentId;
    }

    public static void update(Order order){
        delete(order.getId());
        data.put(order.getId(), order);
    }

    public static void delete(Order order){
        data.remove(order.getId());
    }

    public static void delete(Long orderId){
        data.remove(orderId);
    }

    public static Map<Long, Order> getData() {
        return data;
    }
}

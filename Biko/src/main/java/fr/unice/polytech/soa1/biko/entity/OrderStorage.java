package fr.unice.polytech.soa1.biko.entity;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Nabil on 30/09/2015.
 */
public class OrderStorage {

    private static long id = 0;

    private static HashMap<Long, Order> orders = new HashMap<>();

    public static void add(Order order){
        orders.put(id, order);
        order.setId(id);
        order.setTracking(id);
        id++;
    }

    public static Order read (long id){
        return orders.get(id);
    }

    public static void delete (long id){
        orders.remove(id);
    }

    public static Collection<Order> getAll (){
        return orders.values();
    }

}

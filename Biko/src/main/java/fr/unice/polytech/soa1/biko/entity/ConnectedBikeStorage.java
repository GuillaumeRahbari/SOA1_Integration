package fr.unice.polytech.soa1.biko.entity;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Quentin Cornevin & Nabil El Moussaid
 *
 * This class allow us to mock a database with bike in it.
 */
public class ConnectedBikeStorage {

    private static HashMap<Long, ConnectedBike> connectedBikeDB = new HashMap<>();

    public static void create(String name, String color, long id, int price) {
        connectedBikeDB.put(id, new ConnectedBike(name, color, id, price));
    }

    public static ConnectedBike read(Long id) {
        return connectedBikeDB.get(id);
    }

    public static void delete(String name) {
        connectedBikeDB.remove(name);
    }

    public static Collection<ConnectedBike> findAll() {
        return connectedBikeDB.values();
    }

    public static void add(ConnectedBike connectedBike) {
        connectedBikeDB.put(connectedBike.getId(),connectedBike);
    }

    static {
        ConnectedBikeStorage.create("bike1", "red", 123456789, 10);
        ConnectedBikeStorage.create("bike2", "blue", 133456789, 100);
        ConnectedBikeStorage.create("bike3", "green", 143456789, 1000);
    }



}

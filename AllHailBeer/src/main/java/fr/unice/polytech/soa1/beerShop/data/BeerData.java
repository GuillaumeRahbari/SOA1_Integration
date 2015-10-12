package fr.unice.polytech.soa1.beerShop.data;

import fr.unice.polytech.soa1.beerShop.model.Beer;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tom on 28/09/15.
 */
public class BeerData {
    private static Map<String, Beer> data = DaoUtils.readData("beerData.json", String.class, Beer.class);

    public static Beer get(String id){
        return data.get(id);
    }

    public static void add(Beer beer){
        data.put(beer.getName(),beer);
    }

    public static void update(Beer beer){
        delete(beer);
        data.put(beer.getName(), beer);
    }

    public static void delete(Beer beer){
        data.remove(beer.getName());
    }

    public static Map<String, Beer> getData() {
        return data;
    }

}

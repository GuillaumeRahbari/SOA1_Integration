package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Laureen Ginier
 * Parse the json array of items coming from AllHailBeer shop and map it to a list of CatalogItem.
 */
public class TransformResponseBeer extends TransformResponse {

    public TransformResponseBeer(String shopName) {
        super(shopName);
    }

    @Override
    public List<CatalogItem> mapToCatalogItem(String jsonString) throws Exception {
        JSONArray jarray = new JSONArray(jsonString);
        List<CatalogItem> items = new ArrayList<CatalogItem>();
        for(int i = 0; i < jarray.length(); i++) {
            JSONObject obj = jarray.getJSONObject(i);
            for (Iterator iterator = obj.keys(); iterator.hasNext();) {
                String key = (String) iterator.next();
                JSONObject jobj = obj.getJSONObject(key);
                String name = jobj.getString("name");
                String price = jobj.getString("pricePerLiter");
                String descr = "Titration: " + jobj.getString("titration")
                        + ", gout: " + jobj.getString("gout")
                        + ", cereale: " + jobj.getString("cereale");
                items.add(new CatalogItem(name, Double.parseDouble(price), descr));
            }
        }
        return items;
    }
}
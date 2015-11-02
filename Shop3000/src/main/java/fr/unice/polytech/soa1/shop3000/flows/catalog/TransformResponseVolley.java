package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Laureen Ginier
 */
public class TransformResponseVolley extends TransformResponse {

    public TransformResponseVolley(String shopName) {
        super(shopName);
    }

    @Override
    public List<CatalogItem> parse(JSONArray jarray) throws Exception {
        List<CatalogItem> items = new ArrayList<CatalogItem>();
        for(int i = 0; i < jarray.length(); i++) {
            JSONObject jobj = jarray.getJSONObject(i);
            String name = jobj.getString("name");
            String price = jobj.getString("price");
            String descr = jobj.getString("description");
            items.add(new CatalogItem(name, Double.parseDouble(price), descr));
        }
        return items;
    }
}

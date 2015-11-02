package fr.unice.polytech.soa1.shop3000.flows.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.CatalogItemBiko;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Laureen Ginier
 */
public class TransformResponseBiko extends TransformResponse {

    @Override
    public void process(Exchange exchange) throws Exception {
        InputStream response = (InputStream) exchange.getIn().getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { out.append(line); }
        reader.close();

        ObjectMapper mapper = new ObjectMapper();
        List<CatalogItemBiko> items = mapper.readValue(out.toString(),
                new TypeReference<List<CatalogItemBiko>>() { } );

        String jsonString = "{\"shopName\":\""+shopName+"\", \"items\":"
                + mapper.writeValueAsString(items) + "}";

        exchange.getIn().setBody(jsonString);
    }

    public TransformResponseBiko(String shopName) {
        super(shopName);
    }

    public List<CatalogItem> parse(JSONArray jarray) throws Exception {
        List<CatalogItem> items = new ArrayList<CatalogItem>();
        for(int i = 0; i < jarray.length(); i++) {
            JSONObject jobj = jarray.getJSONObject(i);
            String name = jobj.getString("name");
            String price = jobj.getString("price");
            String descr = jobj.getString("color");
            items.add(new CatalogItem(name, Double.parseDouble(price), descr));
        }
        return items;
    }
}

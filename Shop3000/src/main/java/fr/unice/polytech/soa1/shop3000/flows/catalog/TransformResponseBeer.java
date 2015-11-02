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
 */
public class TransformResponseBeer implements Processor {

    private String shopName;

    public TransformResponseBeer(String shopName) {
        this.shopName = shopName;
    }

    public List<CatalogItem> parse(JSONArray jarray) throws Exception {
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
                String custom = jobj.getString("user");
                boolean customization = !"".equals(custom);

                items.add(new CatalogItem(name, Double.parseDouble(price), descr));
            }

        }
        return items;
    }

    public void process(Exchange exchange) throws Exception {
        InputStream response = (InputStream) exchange.getIn().getBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) { out.append(line); }
        reader.close();

        JSONArray jarray = new JSONArray(out.toString());

        List<CatalogItem> items = parse(jarray);

        StringBuilder jsonstring = new StringBuilder();
        jsonstring.append("{\"shopName\":\""+shopName+"\", \"items\":[");

        boolean first = true;
        for(CatalogItem item : items){
            jsonstring.append(first ? "" : ",");
            first = false;
            jsonstring.append(item.toJsonString());
        }
        jsonstring.append("]}");

        exchange.getIn().setBody(jsonstring.toString());
    }
}
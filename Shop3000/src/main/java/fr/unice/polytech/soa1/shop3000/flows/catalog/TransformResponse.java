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
import java.util.List;

/**
 * @author Laureen Ginier
 * Read the response and formats it into a list of POJO
 */
public abstract class TransformResponse implements Processor {

    private String shopName;

    public TransformResponse(String shopName){
        this.shopName = shopName;
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
        jsonstring.append("{\"shopName\":\""+shopName+"\", \"items\":");
        jsonstring.append("[");

        boolean first = true;
        for(CatalogItem item : items){
            jsonstring.append(first ? "" : ",");
            first = false;
            jsonstring.append(item.toString());
        }
        jsonstring.append("]}");

        exchange.getIn().setBody(jsonstring.toString());
    }

    public abstract List<CatalogItem> parse(JSONArray jarray) throws Exception;
}

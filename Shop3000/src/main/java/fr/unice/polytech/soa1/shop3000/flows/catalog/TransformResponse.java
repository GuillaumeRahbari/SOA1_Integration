package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.codehaus.jettison.json.JSONArray;

import java.io.InputStream;
import java.util.List;

/**
 * @author Laureen Ginier
 * Read the response and formats it into a list of POJO
 */
public abstract class TransformResponse extends SuperProcessor {

    private String shopName;

    public TransformResponse(String shopName){
        this.shopName = shopName;
    }

    public void process(Exchange exchange) throws Exception {
        String out = getStringFromInputStream((InputStream) exchange.getIn().getBody());
        JSONArray jarray = new JSONArray(out);

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

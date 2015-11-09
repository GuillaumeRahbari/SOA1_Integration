package fr.unice.polytech.soa1.shop3000.flows.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.Catalog;
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
 * Map the json array of items coming from Biko shop to a list of CatalogItem.
 */
public class TransformResponseBiko extends TransformResponse {

    public TransformResponseBiko(String shopName) {
        super(shopName);
    }

    @Override
    public List<CatalogItem> mapToCatalogItem(String jsonString) throws Exception {
        List<CatalogItem> items = new ObjectMapper().readValue(jsonString, new TypeReference<List<CatalogItemBiko>>() {});
        Catalog.getInstance().setItemsBiko(items);
        return items;
    }
}

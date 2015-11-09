package fr.unice.polytech.soa1.shop3000.flows.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.Catalog;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.CatalogItemVolley;
import java.util.List;

/**
 * @author Laureen Ginier
 * Map the json array of items coming from VolleyOnTheBeach shop to a list of CatalogItem.
 */
public class TransformResponseVolley extends TransformResponse {

    public TransformResponseVolley(String shopName) {
        super(shopName);
    }

    @Override
    public List<CatalogItem> mapToCatalogItem(String jsonString) throws Exception {
        List<CatalogItem> items = new ObjectMapper().readValue(jsonString, new TypeReference<List<CatalogItemVolley>>() {});
        Catalog.getInstance().setItemsVolley(items);
        return items;
    }
}

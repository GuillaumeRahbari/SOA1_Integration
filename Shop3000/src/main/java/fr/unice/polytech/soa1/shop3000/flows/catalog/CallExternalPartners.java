package fr.unice.polytech.soa1.shop3000.flows.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.catalog.*;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.Shop;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Quentin Cornevin
 * Updated by Laureen
 * These flows calls the shops' services to get their catalog and formats the results
 */
public class CallExternalPartners extends RouteBuilder {

    private TransformResponseBiko transformBiko;
    private TransformResponseVolley transformVolley;
    private TransformResponseBeer transformBeer;


    public CallExternalPartners() {
        this.transformBiko = new TransformResponseBiko(Shop.BIKO.getName());
        this.transformVolley = new TransformResponseVolley(Shop.VOLLEY.getName());
        this.transformBeer = new TransformResponseBeer(Shop.BEER.getName());
    }

    /**
     * In this fail we create the request to the different system to get their catalog
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        /**
         * Gets the Biko shop's catalog and formats the json to uniformize the items and add the shop name
         * Comes from {@link GetCatalogs}
         */
        from(Endpoint.BIKO_CATALOG.getInstruction())
                .log("Begin processing : Get Biko catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")
                /** {@link TransformResponseBiko} **/
                .process(transformBiko)
                .log("${body}");

        /**
         * Gets the VolleyOnTheBeach shop's catalog and formats the json to uniformize the items and add the shop name
         * Comes from {@link GetCatalogs}
         */
        from(Endpoint.VOLLEY_CATALOG.getInstruction())
                .log("Begin processing : Get Volley catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/volley/catalog?bridgeEndpoint=true")
                /** {@link TransformResponseVolley} **/
                .process(transformVolley)
                .log("${body}");

        /**
         * Gets the AllHailBeer shop's catalog and formats the json to uniformize the items and add the shop name
         * Comes from {@link GetCatalogs}
         */
        from(Endpoint.BEER_CATALOG.getInstruction())
                .log("Begin processing : Get Beer catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/shop/beers/all?bridgeEndpoint=true")
                /** {@link TransformResponseBeer} **/
                .process(transformBeer)
                .log("${body}");
    }

    /**
     * @author Laureen Ginier
     * Read the response, which is a json array of items from a shop, and formats it into a json string
     * containing the list of standardized CatalogItems.
     */
    private abstract class TransformCatalogResponse extends SuperProcessor {
        protected String shopName;

        public TransformCatalogResponse(String shopName) {
            this.shopName = shopName;
        }

        @Override
        public void process(Exchange exchange) throws Exception {
            String out = extractExchangeBody(exchange);

            ObjectMapper mapper = new ObjectMapper();
            List<CatalogItem> items = mapToCatalogItem(out);
            String jsonString = mapper.writeValueAsString(items);
            exchange.getIn().setBody(jsonString.replace("[","").replace("]",""));
        }

        public abstract List<CatalogItem> mapToCatalogItem(String jsonString) throws Exception;
    }

    /**
     * @author Laureen Ginier
     * Parse the json array of items coming from AllHailBeer shop and map it to a list of CatalogItem.
     */
    private class TransformResponseBeer extends TransformCatalogResponse {

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
                    CatalogItem beerItem = new CatalogItem(name, Double.parseDouble(price), new ItemDescription(jobj.getString("titration") ,  jobj.getString("gout"), jobj.getString("cereale")), null);
                    items.add(beerItem);
                }
            }
            Catalog.getInstance().setItemsBeer(items);
            return items;
        }
    }

    /**
     * @author Laureen Ginier
     * Map the json array of items coming from Biko shop to a list of CatalogItem.
     */
    public class TransformResponseBiko extends TransformCatalogResponse {

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

    /**
     * @author Laureen Ginier
     * Map the json array of items coming from VolleyOnTheBeach shop to a list of CatalogItem.
     */
    public class TransformResponseVolley extends TransformCatalogResponse {

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

}

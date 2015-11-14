package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.catalog.Catalog;
import fr.unice.polytech.soa1.shop3000.business.catalog.CatalogItem;
import fr.unice.polytech.soa1.shop3000.utils.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

/**
 * Created by Quentin on 10/21/2015.
 * Updated by Laureen
 */
public class GetCatalogs extends RouteBuilder {

    private ToJSonArray toJsonArray = new ToJSonArray();
    private GetVolleyItemName getVolleyItemName = new GetVolleyItemName();

    /**
     * This is the flow for the catalog. We make asynchrone request to the biko, volley and Hailbeer system, then we make
     * an aggregation of the several body with the aggregation strategy define in JoinAggregationStrategy
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        /**
         * Begin of the flow to get the shops catalogs and merge them into a standard one
         * It redirects to {@link CallExternalPartners#configure()}
         */
        from(Endpoint.GET_CATALOG.getInstruction())
            .log("Start get catalogs processing")
            .multicast()
                .aggregationStrategy(new JoinAggregationStrategy())
                .parallelProcessing()
                .log("Parallel processing")
                .to(Endpoint.BIKO_CATALOG.getInstruction())
                .to(Endpoint.VOLLEY_CATALOG.getInstruction())
                .to(Endpoint.BEER_CATALOG.getInstruction())
                .end()
            /** {@link ToJSonArray} **/
            .process(toJsonArray)
                .log("${body}");

        from(Endpoint.GET_BEST_SELLER.getInstruction())
            .log("Begin Get BestSeller")
                /**
                 * {@link BestSellerBean#getBestSeller()}
                 */
            .bean(BestSellerBean.class, "getBestSeller()");

        /**
         * Gets the cutomization available for volley items.
         * Provides a json array of available colors.
         */
        from(Endpoint.CUSTOM_VOLLEY.getInstruction())
                .log("Begin : get Custom Volley")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                /**
                 * {@link GetCatalogs#getVolleyItemName}
                 */
                .process(getVolleyItemName)
                .recipientList(simple("http://localhost:8181/cxf/volley/catalog/${property.volleyItemName}/colors?bridgeEndpoint=true"))
                .log("$body");

        from(Endpoint.CUSTOM_BIKO.getInstruction())
                .log("Begin : get custom Biko")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog/component/types/wheel?bridgeEndpoint=true")
                .log("$body");

    }

    /**
     * @author Laureen Ginier
     * Encapsulate the exchange into '[]' to have a valid json array
     */
    private class ToJSonArray implements Processor {

        public void process(Exchange exchange) throws Exception {
            String response = (String) exchange.getIn().getBody();
            System.out.println(response);
            String jsonResponse = "[" + response + "]";
            exchange.getIn().setBody(jsonResponse);
        }
    }


    public class GetVolleyItemName extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            List<CatalogItem> catalog = Catalog.getInstance().getItemsVolley();
            if(!catalog.isEmpty()) {
                exchange.setProperty("volleyItemName", catalog.get(0).getName());
            }
        }
    }
}

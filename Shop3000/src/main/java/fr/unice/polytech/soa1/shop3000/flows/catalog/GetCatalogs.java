package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.Catalog;
import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/21/2015.
 * Updated by Laureen
 */
public class GetCatalogs extends RouteBuilder {

    private ToJSonArray toJsonArray = new ToJSonArray();

    /**
     * This is the flow for the catalog. We make asynchrone request to the biko, volley and Hailbeer system, then we make
     * an aggregation of the several body with the aggregation strategy define in JoinAggregationStrategy
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        /**
         * Begin of the flow to get the shops catalogs and merge them into a standard one
         * It redirects to {@link CallExternalPartners}
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
                .log("Begin Get BestSeller");
    }

    /**
     * @author Laureen Ginier
     * Encapsulate the exchange into '[]' to have a valid json array
     */
    private class ToJSonArray implements Processor {

        public void process(Exchange exchange) throws Exception {
            String response = (String) exchange.getIn().getBody();
            String jsonResponse = "[" + response + "]";
            exchange.getIn().setBody(jsonResponse);
        }
    }
}

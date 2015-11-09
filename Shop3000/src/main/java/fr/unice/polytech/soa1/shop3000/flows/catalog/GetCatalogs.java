package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/21/2015.
 */
public class GetCatalogs extends RouteBuilder {

    private JSonTransform jSonTransform = new JSonTransform();

    /**
     * This is the flow for the catalog. We make asynchrone request to the biko, volley and Hailbeer system, then we make
     * an aggregation of the several body with the aggregation strategy define in JoinAggregationStrategy
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        // This flow gets the different shops' catalogs and aggregates the results into one catalog
        from(Endpoint.GET_CATALOG.getInstruction())
                .log("Start get catalog Processing")
                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy())
                    .parallelProcessing()
                    .log("Test parallele processing")
                    .log("Seconde test")
                    .to(Endpoint.BIKO_CATALOG.getInstruction())
                    .to(Endpoint.VOLLEY_CATALOG.getInstruction())
                    .to(Endpoint.BEER_CATALOG.getInstruction())
                    .end()
                .process(jSonTransform)
                    .log("${body}");


        from(Endpoint.GET_BEST_SELLER.getInstruction())
                .log("Begin Get BestSeller");


    }
}

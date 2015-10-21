package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/21/2015.
 */
public class GetCatalogs extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(Endpoints.CATALOG_TEST)
                .log("Start get catalog Processing")
                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy())
                    .parallelProcessing()
                    .log("Test parallele processing")
                    .log("Seconde test")
                    .to(Endpoints.BIKO_CATALOG)
                    .to(Endpoints.VOLLEY_CATALOG)
                    .to(Endpoints.BEER_CATALOG)
                    .end()
                    .log("${body}");



    }
}

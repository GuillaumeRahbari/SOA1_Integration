package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/21/2015.
 */
public class GetCatalogs extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(Endpoint.CATALOG_TEST.getInstruction())
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
                    .log("${body}");



    }
}

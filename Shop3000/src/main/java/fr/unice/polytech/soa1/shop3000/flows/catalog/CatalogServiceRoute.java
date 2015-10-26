package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/25/2015.
 */
public class CatalogServiceRoute extends RouteBuilder {

    /**
     * In this method we configure all the root to access the catalog.
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet"); // feature:install camel-servlet + edit in the OSGi blueprint

        rest("/catalog")
                .get()
                .to(Endpoint.GET_CATALOG.getInstruction());


    }

}

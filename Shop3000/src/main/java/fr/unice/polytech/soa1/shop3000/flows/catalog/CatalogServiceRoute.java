package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/25/2015.
 * This service allows to get a formatted catalog which is an aggregation of all the different shops' catalogs.
 */
public class CatalogServiceRoute extends RouteBuilder {

    /**
     * In this method we configure all the root to access the catalog.

     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet"); // feature:install camel-servlet + edit in the OSGi blueprint

        /**
         * This flow gets the shop3000 catalog.
         * {@link GetCatalogs#configure()}
         */
        rest("/catalog")
                .get()
                .to(Endpoint.GET_CATALOG.getInstruction());


        /**
         * This flow gets the bestseller item of shop3000.
         * {@link GetCatalogs#configure()}
         */
        rest("/catalog/bestSeller")
                .get()
                .to(Endpoint.GET_BEST_SELLER.getInstruction());

        /***
         * This flow gets the customizable colors for volley items
         *{@link GetCatalogs#configure()}
         */
        rest("/catalog/customization/volley")
                .get()
                .to(Endpoint.CUSTOM_VOLLEY.getInstruction());

        /**
         * This flow gets the customizations available for bike items.
         * {@link GetCatalogs#configure()}
         */
        rest("/catalog/customization/bike")
                .get()
                .to(Endpoint.CUSTOM_BIKO.getInstruction());

    }

}

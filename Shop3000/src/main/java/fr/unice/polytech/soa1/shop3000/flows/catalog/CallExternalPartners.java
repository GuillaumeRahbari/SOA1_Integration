package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Quentin Cornevin
 * These flows calls the shops' services to get their catalog and formats the results
 */
public class CallExternalPartners extends RouteBuilder {

    private TransformResponseBiko transformBiko;
    private TransformResponseVolley transformVolley;
    private TransformResponseBeer transformBeer;


    public CallExternalPartners() {
        this.transformBiko = new TransformResponseBiko("biko");
        this.transformVolley = new TransformResponseVolley("volleyonthebeach");
        this.transformBeer = new TransformResponseBeer("allhailbeer");
    }

    /**
     * In this fail we create the request to the different system to get their catalog
     *
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        // Gets the Biko shop's catalog and formats the json to uniformize the items and add the shop name
        from(Endpoint.BIKO_CATALOG.getInstruction())
                .log("Begin processing : Get Biko catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")
                .process(transformBiko)
                .log("${body}");

        // Gets the VolleyOnTheBeach shop's catalog and formats the json to uniformize the items and add the shop name
        from(Endpoint.VOLLEY_CATALOG.getInstruction())
                .log("Begin processing : Get Volley catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/volley/catalog?bridgeEndpoint=true")
                .process(transformVolley)
                .log("${body}");

        // Gets the AllHailBeer shop's catalog and formats the json to uniformize the items and add the shop name
        from(Endpoint.BEER_CATALOG.getInstruction())
                .log("Begin processing : Get Beer catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/shop/beers/all?bridgeEndpoint=true")
                .process(transformBeer)
                .log("${body}");
    }
}

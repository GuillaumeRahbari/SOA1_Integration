package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Quentin Cornevin
 * These flows calls the shops' services to get their catalog and formats the results
 */
public class CallExternalPartners extends RouteBuilder {

    //private ReadResponseStream readResponseStreamBiko;
    //private ReadResponseStream readResponseStreamVolley;
    private ReadResponseStream readResponseStreamBeer;
    private TransformResponseBiko transformBiko;
    private TransformResponseVolley transformVolley;
    private TransformResponseBeer transformBeer;


    public CallExternalPartners() {
        //this.readResponseStreamBiko = new ReadResponseStream("biko");
        //this.readResponseStreamVolley = new ReadResponseStream("volleyonthebeach");
        this.readResponseStreamBeer = new ReadResponseStream("allhailbeer");
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
        // Gets the Biko shop's catalog and formats the json to add the shop name
        from(Endpoint.BIKO_CATALOG.getInstruction())
                .log("Begin processing : Get Biko catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")
                .setProperty("shopName", constant("biko"))
                //.unmarshal().json(JsonLibrary.Jackson,CatalogItem.class)
                .process(transformBiko)
                //.process(readResponseStreamBiko)
                .log("${body}");

        // Gets the VolleyOnTheBeach shop's catalog and formats the json to add the shop name
        from(Endpoint.VOLLEY_CATALOG.getInstruction())
                .log("Begin processing : Get Volley catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/volley/catalog?bridgeEndpoint=true")
                .setProperty("shopName", constant("volleyonthebeach"))
                //.unmarshal().json(JsonLibrary.Jackson, CatalogItem.class)
                .process(transformVolley)
                //.process(readResponseStreamVolley)
                .log("${body}");

        // Gets the AllHailBeer shop's catalog and formats the json to add the shop name
        from(Endpoint.BEER_CATALOG.getInstruction())
                .log("Begin processing : Get Beer catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/shop/beers/all?bridgeEndpoint=true")
                .setProperty("shopName", constant("allhailbeer"))
                //.unmarshal().json(JsonLibrary.Jackson, CatalogItem.class)
                .process(transformBeer)
                //.process(readResponseStreamBeer)
                .log("${body}");
    }
}

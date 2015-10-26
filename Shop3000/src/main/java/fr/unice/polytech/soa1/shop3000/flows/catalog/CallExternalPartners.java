package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * Created by Quentin on 10/21/2015.
 */
public class CallExternalPartners extends RouteBuilder {

    private ReadResponseStream readResponseStreamBiko;
    private ReadResponseStream readResponseStreamVolley;
    private ReadResponseStream readResponseStreamBeer;


    public CallExternalPartners() {
        this.readResponseStreamBiko = new ReadResponseStream("biko");
        this.readResponseStreamVolley = new ReadResponseStream("volleyonthebeach");
        this.readResponseStreamBeer = new ReadResponseStream("allhailbeer");
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.BIKO_CATALOG.getInstruction())
                .log("Begin processing : Get Biko catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")
                .setProperty("shopName", constant("biko"))
                //.unmarshal().json(JsonLibrary.Jackson,CatalogItem.class)
                .process(readResponseStreamBiko)
                .log("${body}");

        from(Endpoint.VOLLEY_CATALOG.getInstruction())
                .log("Begin processing : Get Volley catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/volley/catalog?bridgeEndpoint=true")
                .setProperty("shopName", constant("volleyonthebeach"))
                //.unmarshal().json(JsonLibrary.Jackson, CatalogItem.class)
                .process(readResponseStreamVolley)
                .log("${body}");

        from(Endpoint.BEER_CATALOG.getInstruction())
                .log("Begin processing : Get Beer catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/shop/beers/all?bridgeEndpoint=true")
                .setProperty("shopName", constant("allhailbeer"))
                //.unmarshal().json(JsonLibrary.Jackson, CatalogItem.class)
                .process(readResponseStreamBeer)
                .log("${body}");
    }
}

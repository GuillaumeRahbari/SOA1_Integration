package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/21/2015.
 */
public class CallExternalPartners extends RouteBuilder {

    private ReadResponseStream readResponseStream;

    public CallExternalPartners() {
        this.readResponseStream = new ReadResponseStream();
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.BIKO_CATALOG.getInstruction())
                .log("Begin processing : Get Biko catalog")
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog?bridgeEndpoint=true")
                .process(readResponseStream)
                .log("${body}");

        from(Endpoint.VOLLEY_CATALOG.getInstruction())
                .log("Begin processing : Get Volley catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/volley/catalog?bridgeEndpoint=true")
                .process(readResponseStream)
                .log("${body}");

        from(Endpoint.BEER_CATALOG.getInstruction())
                .log("Begin processing : Get Beer catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/shop/beers/all?bridgeEndpoint=true")
                .process(readResponseStream)
                .log("${body}");
    }
}

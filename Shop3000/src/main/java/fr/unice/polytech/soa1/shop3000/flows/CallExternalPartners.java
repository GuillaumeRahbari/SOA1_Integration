package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.process.ReadResponseStream;
import fr.unice.polytech.soa1.shop3000.utils.Endpoints;
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
        from(Endpoints.BIKO_CATALOG)
                .log("Begin processing : Get Biko catalog")
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/biko/catalog")
                .process(readResponseStream)
                .log("${body}");

        from(Endpoints.VOLLEY_CATALOG)
                .log("Begin processing : Get Volley catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/volley/catalog")
                .process(readResponseStream)
                .log("${body}");

        from(Endpoints.BEER_CATALOG)
                .log("Begin processing : Get Beer catalog")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .to("http://localhost:8181/cxf/shop/beers/all")
                .process(readResponseStream)
                .log("${body}");
    }
}

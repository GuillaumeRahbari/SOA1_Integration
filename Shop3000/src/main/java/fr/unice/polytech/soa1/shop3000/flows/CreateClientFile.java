package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/19/2015.
 */
public class CreateClientFile extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(Endpoints.CLIENT_FILE_INPUT)
                .to(Endpoints.CLIENT_DATABASE)
                .filter(simple("properties:bdok"))
                .log("After filter")
                ;

    }
}

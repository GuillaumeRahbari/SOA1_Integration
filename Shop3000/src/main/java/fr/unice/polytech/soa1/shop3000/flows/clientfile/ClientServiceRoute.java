package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/24/2015.
 */
public class ClientServiceRoute extends RouteBuilder{


    @Override
    public void configure() throws Exception {

        // Binding the REST domain specific language to the Servlet component
        restConfiguration().component("servlet"); // feature:install camel-servlet + edit in the OSGi blueprint

        // Defining the resource to expose, and the used verb
        rest("/clientFile/create")
                .post()
                .to(Endpoint.CLIENT_FILE_INPUT.getInstruction())

        ;
    }
}

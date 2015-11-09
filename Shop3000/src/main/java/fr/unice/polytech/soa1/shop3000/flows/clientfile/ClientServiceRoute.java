package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/24/2015.
 */
public class ClientServiceRoute extends RouteBuilder{


    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        /**
         * This flow create a client in the shop3000 database
         *
         */
        rest("/clientFile")
                .post()
                .to(Endpoint.CLIENT_UNMARSHALL.getInstruction());

        /**
         * TODO : Mettre une gestion des cas d'erreur pour le post /clientFile
         * TODO : Faire un get /clientFile/{firstName}
         * TODO : Faire un Delete /clientFile/{firstName}
         */
    }
}

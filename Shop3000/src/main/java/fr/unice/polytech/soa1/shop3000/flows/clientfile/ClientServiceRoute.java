package fr.unice.polytech.soa1.shop3000.flows.clientfile;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/24/2015.
 */
public class ClientServiceRoute extends RouteBuilder{


    /**
     * Root definitions for client.
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        /**
         * This flow create a client in the shop3000 database.
         *
         * This flow continues to {@link Unmarshaller}.
         */
        rest("/clientFile")
                .post()
                .to(Endpoint.CLIENT_UNMARSHALL.getInstruction());

        /**
         * TODO : Mettre une gestion des cas d'erreur pour le post /clientFile
         * TODO : Faire un get /clientFile/{firstName}
         * TODO : Faire un Delete /clientFile/{firstName}
         */

        /**
         * This flows get a client in the shop3000 database thanks to the users's first name.
         *
         * This flow continues to {@link ClientFileFlows}.
         */
        rest("/clientfile/{firstName}")
                .get()
                .to(Endpoint.GET_CLIENT_FILE.getInstruction());


        /**
         * This flows delete a client in the shop3000 database thanks to the user's first name.
         *
         * This flow continues to {@link ClientFileFlows}.
         */
        rest("/clientfile/{firstName}")
                .delete()
                .to(Endpoint.DELETE_CLIENT_FILE.getInstruction());
    }
}

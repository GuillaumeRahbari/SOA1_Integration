package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.filter.ClientRegistered;
import fr.unice.polytech.soa1.shop3000.process.mock.ClientFileMock;
import fr.unice.polytech.soa1.shop3000.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Quentin Cornevin
 *
 * This class represent the flow which handle the creation of a Client file in the store 300 system.
 */
public class CreateClientFile extends RouteBuilder {

    /**
     * This class create a process that will mock the fact we don't have a real input and put a fake client in the body.
     */
    private ClientFileMock clientFileMock = new ClientFileMock();

    @Override
    public void configure() throws Exception {
        from(Endpoints.CLIENT_FILE_INPUT)
                .log("Start processing")
                .process(clientFileMock)
                .log("Fake client addded")
                .log("Body : ")
                .log("FirstName of the client : ${body.firstName}")
                .log("LastName of the client : ${body.lastName}")
                .log("Begin of the filter, it is the last step if the client is already in the database")
                .filter()
                .method(new ClientRegistered(), "filter")
                .log("After filter")
                .log("End of the process");

    }
}

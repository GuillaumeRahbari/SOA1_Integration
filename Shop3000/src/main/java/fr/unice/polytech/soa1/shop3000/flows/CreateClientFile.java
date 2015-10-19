package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.filter.ClientRegistered;
import fr.unice.polytech.soa1.shop3000.process.ClientCheckProcess;
import fr.unice.polytech.soa1.shop3000.process.mock.ClientFileMock;
import fr.unice.polytech.soa1.shop3000.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by Quentin on 10/19/2015.
 */
public class CreateClientFile extends RouteBuilder {

    private ClientCheckProcess clientCheckProcess = new ClientCheckProcess();
    private ClientFileMock clientFileMock = new ClientFileMock();

    @Override
    public void configure() throws Exception {
        from(Endpoints.CLIENT_FILE_INPUT)
                .log("Processing ${file:name}")
                .process(clientFileMock)
                .log(" After process")
                .log("Body : ${body.firstName}")
                .filter()
                .method(new ClientRegistered(), "filter")
                .log("After filter")
                .log("Body : ${body.firstName}");

    }
}

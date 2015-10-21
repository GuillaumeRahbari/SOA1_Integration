package fr.unice.polytech.soa1.shop3000.flows;

import fr.unice.polytech.soa1.shop3000.filter.ClientRegistered;
import fr.unice.polytech.soa1.shop3000.process.AddClientToDataBase;
import fr.unice.polytech.soa1.shop3000.process.mock.ClientFileMock;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
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
    private ClientFileMock clientFileMock;
    private AddClientToDataBase addClientToDataBase;

    public CreateClientFile() {
        this.clientFileMock = new ClientFileMock();
        this.addClientToDataBase = new AddClientToDataBase();
    }

    @Override
    public void configure() throws Exception {
        from(Endpoint.CLIENT_FILE_INPUT.getInstruction())
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
                .log("Begin process to add the client in the database")
                .process(addClientToDataBase)
                .log("Client added to the database")
                .log("End of the process")
                .to(Endpoint.CATALOG_TEST.getInstruction());


    }
}

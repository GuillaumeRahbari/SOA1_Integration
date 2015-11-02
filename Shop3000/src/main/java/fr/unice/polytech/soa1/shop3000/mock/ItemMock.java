package fr.unice.polytech.soa1.shop3000.mock;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


/**
 * @author Quentin Cornevin
 *
 * This class mock the creation of an item in the body of the message
 *
 */
public class ItemMock implements Processor {

    /**
     * In this method we get a validate catalog with the mock.
     * And we set the "item" property with this validate catalog.
     * We then get a client from the db.
     * And we set the "client" property with this client.
     * Then we set the "shopName" property with the "Biko" value.
     *
     * @param exchange Empty.
     * @throws Exception
     */
    public void process(Exchange exchange) throws Exception {
        CatalogItem catalogItem = new CatalogItem(MockedData.ITEM_NAME,MockedData.ITEM_PRICE);
        //exchange.getIn().setBody(catalogItem);

        exchange.setProperty("item", catalogItem);
        Client client = ClientStorage.read("Quentin");
        //exchange.getIn().setBody(client);
        exchange.setProperty("client", client);
        // We need a shopName to know where to add.
        exchange.setProperty("shopName", "Biko");



    }
}

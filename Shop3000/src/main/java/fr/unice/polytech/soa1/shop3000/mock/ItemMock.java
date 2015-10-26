package fr.unice.polytech.soa1.shop3000.mock;

import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


/**
 * @author Quentin Cornevin
 *
 * This class mocj the creation of an item in the body of the message
 *
 */
public class ItemMock implements Processor {

    public void process(Exchange exchange) throws Exception {
        CatalogItem catalogItem = new CatalogItem(MockedData.ITEM_NAME,MockedData.ITEM_PRICE);
        //exchange.getIn().setBody(catalogItem);
        exchange.setProperty("item", catalogItem);
        Client client = ClientStorage.read("Quentin");
        //exchange.getIn().setBody(client);
        exchange.setProperty("client", client);

        exchange.setProperty("shopName", "Biko");



    }
}

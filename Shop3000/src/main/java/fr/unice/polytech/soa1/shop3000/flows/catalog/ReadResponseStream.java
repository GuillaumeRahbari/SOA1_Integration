package fr.unice.polytech.soa1.shop3000.flows.catalog;

import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;

import java.io.InputStream;

/**
 * Created by Quentin on 10/21/2015.
 * Read the response, formats it into a json string and add the shop name
 */
public class ReadResponseStream extends SuperProcessor {

    private String shopName;

    public ReadResponseStream(String shopName){
        this.shopName = shopName;
    }

    /**
     * This method take the body of the exchange andd add the name of the shop to create several JSON Object
     * {"shopName":"A",Items:[....]}
     * {"shopName":"B",Items:[....]}
     * {"shopName":"C",Items:[....]}
     *
     * This work is in progress we want to put the JSON OBjects in a JSONArray
     *
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        String out = "{\"shopName\":\"" + shopName + "\",\"items\":"
                + getStringFromInputStream((InputStream) exchange.getIn().getBody()) + "}";

        exchange.getIn().setBody(out);
    }
}

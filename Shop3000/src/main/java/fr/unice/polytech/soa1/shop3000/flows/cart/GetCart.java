package fr.unice.polytech.soa1.shop3000.flows.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.Cart;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.Shop;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Laureen Ginier
 */
public class GetCart extends RouteBuilder {

    private GetClientCart getClientCart = new GetClientCart();
    private final String CLIENT_ID_PROPERTY = "clientID";

    @Override
    public void configure() throws Exception {
        /**
         * Flow to get the client cart.
         * Comes from {@link CartRoute}
         */
        from(Endpoint.GET_CART.getInstruction())
                .log("Starting process : Get Cart")
                .setProperty(CLIENT_ID_PROPERTY, simple("${header.clientId}"))
                .log("client: ${property." + CLIENT_ID_PROPERTY + "}")
                /** {@link fr.unice.polytech.soa1.shop3000.flows.cart.GetCart.GetClientCart} **/
                .process(getClientCart);
    }

    /**
     * Puts the client cart in the body as a json array.
     * Example of jsonArray:
     * [{"name":"Hankook","price":20.0,"description":"Titration: 20%"},
     * {"name":"volley ball","price":4.0,"description":""}]
     */
    private class GetClientCart extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Client client = ClientStorage.read((String) exchange.getProperty(CLIENT_ID_PROPERTY));
            if(client != null) {
                Cart cart = client.getCart();
                List<CatalogItem> fullList = new ArrayList<CatalogItem>();
                fullList.addAll(cart.get(Shop.BEER.getName()));
                fullList.addAll(cart.get(Shop.BIKO.getName()));
                fullList.addAll(cart.get(Shop.VOLLEY.getName()));
                ObjectMapper mapper = new ObjectMapper();
                exchange.getIn().setBody(mapper.writeValueAsString(fullList));
            }
        }
    }
}

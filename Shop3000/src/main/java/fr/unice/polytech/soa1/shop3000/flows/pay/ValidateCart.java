package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Cart;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

/**
 * @author Marc Karassev
 *
 * Builds routes responsible for checking cart content.
 */
public class ValidateCart extends RouteBuilder {

    public static final String CART_PROPERTY = "cart";

    private CartExtractor cartExtractor = new CartExtractor();
    private ShopsExtractor shopsExtractor = new ShopsExtractor();

    @Override
    public void configure() throws Exception {

        /**
         * Flow extracting cart.
         */
        from(PayEndpoint.EXTRACT_CART.getInstruction())
                .log("starting cart extraction")
                .process(cartExtractor)
                .choice()
                    .when(exchange -> exchange.getProperty(CART_PROPERTY).equals(PayUnmarshaller.BAD_INFORMATION))
                        .log("bad client id")
                        /** {@link PayRoute#configure() next} route builder **/
                        .to(PayEndpoint.BAD_CLIENT_ID.getInstruction())
                    .otherwise()
                        .log("client id OK")
                        /** {@link ValidateCart#configure() next} route builder **/
                        .to(PayEndpoint.VALIDATE_CART.getInstruction())
                .endChoice();

        /**
         * Flow validating its content by sending to the shops their related products.
         */
        from(PayEndpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
                .wireTap(PayEndpoint.UPDATE_BEST_SELLER.getInstruction())
                /** {@link ShopsExtractor#process(Exchange)} **/
                .process(shopsExtractor)
                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy())
                    .log("multicasting")
                    .parallelProcessing()
                    .to(PayEndpoint.CHECK_CLIENT_BEER.getInstruction())
                    .to(PayEndpoint.CHECK_CLIENT_BIKO.getInstruction())
                    .to(PayEndpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .end()
                .log("merging")
                .log("body: ${body}");
                //.to(PayEndpoint.PAY.getInstruction());

        /**
         * This flow handle the best seller
         */
        from(PayEndpoint.UPDATE_BEST_SELLER.getInstruction())
                .log("Here we update the number of item sells for the best seller");
                // TODO guigui
    }

    /**
     * Process responsible for extracting a client cart.
     * Expects a client id to be set in the "clientID" exchange property.
     * Sets a "cart" exchange property to a cart object if the client exists, otherwise sets this property to "".
     */
    private class CartExtractor extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Client client = ClientStorage.read((String) exchange.getProperty(PayUnmarshaller.CLIENT_ID_PROPERTY));

            if (client != null) {
                Cart cart = client.getCart();

                if (cart != null) {
                    exchange.setProperty(CART_PROPERTY, cart);
                }
                else {
                    exchange.setProperty(CART_PROPERTY, PayUnmarshaller.BAD_INFORMATION);
                }
            }
            else {
                exchange.setProperty(CART_PROPERTY, PayUnmarshaller.BAD_INFORMATION);
            }
        }
    }

    /**
     * Extracts the cart's item in relation to the different shops.
     * Expects a "cart" property to be set.
     * Sets a property for each shop with its related products.
     * In addition, this processor compute the price of all the items in the cart
     * and set a property "price" with the good amount.
     */
    private class ShopsExtractor extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Cart cart = (Cart) exchange.getProperty(CART_PROPERTY);
            double price = 0;

            for (String key: cart.keySet()) {
                List<CatalogItem> items = cart.get(key);
                for(CatalogItem catalogItem : items) {
                    price += catalogItem.getPrice();
                }
                exchange.setProperty(key, cart.get(key));
            }
            exchange.setProperty("price", price);
        }
    }
}

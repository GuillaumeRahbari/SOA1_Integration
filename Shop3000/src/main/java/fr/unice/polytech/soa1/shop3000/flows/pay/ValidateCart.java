package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.business.Cart;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.business.Client;
import fr.unice.polytech.soa1.shop3000.business.ClientStorage;
import fr.unice.polytech.soa1.shop3000.utils.JoinAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayEndpoint;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayProperties;
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

    private CartExtractor cartExtractor = new CartExtractor();
    private ShopsExtractor shopsExtractor = new ShopsExtractor();

    @Override
    public void configure() throws Exception {

        /**
         * Flow extracting cart.
         */
        from(PayEndpoint.EXTRACT_CART.getInstruction())
                .log("starting cart extraction")
                /** {@link CartExtractor#process(Exchange)} **/
                .process(cartExtractor)
                .choice()
                    .when(exchange -> exchange.getProperty(PayProperties.CART_PROPERTY.getInstruction())
                            .equals(PayProperties.BAD_INFORMATION.getInstruction()))
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
                /**
                 * This process add several property on the exchange. On per shop, the property is the name of the shop
                 * and one name "price" with the price of all the items
                 *  {@link ShopsExtractor#process(Exchange)}
                 **/
                .process(shopsExtractor)
                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy())
                    .log("multicasting")
                    .parallelProcessing()
                    /**
                     * Those 3 flows check in the 3 shops if the client exist in their database. If it is not the case
                     * they create the client, then they add all the items of client in the cart
                     *  {@link CheckClientFlow#configure()}
                     **/
                    .to(PayEndpoint.CHECK_CLIENT_BEER.getInstruction())
                    .to(PayEndpoint.CHECK_CLIENT_BIKO.getInstruction())
                    .to(PayEndpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .end()
                .log("merging")

                /** {@link ProceedPayment#configure()} **/
                .to(PayEndpoint.GET_DELIVERY_PRICE.getInstruction());

        /**
         * This flow handle the best seller
         */
        from(PayEndpoint.UPDATE_BEST_SELLER.getInstruction())
                .log("Here we update the number of item sells for the best seller")
                /**
                 * {@link BestSellerBean#updateBestSeller(String)}
                 */
                .bean(BestSellerBean.class, "updateBestSeller(${property.clientID})");
    }




    /**
     * Process responsible for extracting a client cart.
     * Expects a client id to be set in the "clientID" exchange property.
     * Sets a "cart" exchange property to a cart object if the client exists, otherwise sets this property to "".
     */
    private class CartExtractor extends SuperProcessor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Client client = ClientStorage.read(
                    (String) exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction()));

            if (client != null) {
                Cart cart = client.getCart();

                if (cart != null) {
                    exchange.setProperty(PayProperties.CART_PROPERTY.getInstruction(), cart);
                }
                else {
                    exchange.setProperty(PayProperties.CART_PROPERTY.getInstruction(),
                            PayProperties.BAD_INFORMATION.getInstruction());
                }
            }
            else {
                exchange.setProperty(PayProperties.CART_PROPERTY.getInstruction(),
                        PayProperties.BAD_INFORMATION.getInstruction());
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
            Cart cart = (Cart) exchange.getProperty(PayProperties.CART_PROPERTY.getInstruction());
            double price = 0;

            for (String key: cart.keySet()) {
                List<CatalogItem> items = cart.get(key);
                for(CatalogItem catalogItem : items) {
                    price += catalogItem.getPrice();
                }
                exchange.setProperty(key, cart.get(key));
            }
            exchange.setProperty(PayProperties.CART_PRICE_PROPERTY.getInstruction(), price);
        }
    }
}

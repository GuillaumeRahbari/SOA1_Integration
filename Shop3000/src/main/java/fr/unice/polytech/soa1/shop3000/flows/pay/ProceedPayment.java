package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.utils.BooleanAndAggregationStrategy;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayEndpoint;
import fr.unice.polytech.soa1.shop3000.flows.pay.defs.PayProperties;
import fr.unice.polytech.soa1.shop3000.utils.MockDeliverySystem;
import fr.unice.polytech.soa1.shop3000.utils.MockPaymentSystem;
import fr.unice.polytech.soa1.shop3000.utils.Shop3000Information;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes related to payment.
 */
public class ProceedPayment extends RouteBuilder {

    private Payment payment = new Payment();

    private HandleDeliveryPrice handleDeliveryPrice = new HandleDeliveryPrice();

    @Override
    public void configure() throws Exception {

        /** Flow checking payment information.
         *  Expects a property "paymentInformation" with a JSON representing a PaymentInformation object.
         *
         *  List of property of the flow :
         *  - "price" : contain the price of the whole cart added {@link fr.unice.polytech.soa1.shop3000.flows.pay.ValidateCart.ShopsExtractor#process(Exchange)}
         *  -
         *  -
         */
        from(PayEndpoint.VALIDATE_PAYMENT_INFORMATION.getInstruction())
                .log("starting payment information checking")
                .choice()
                .when(exchange -> exchange.getProperty(PayProperties.PAYMENT_INFORMATION_PROPERTY.getInstruction())
                        .equals(PayProperties.BAD_INFORMATION.getInstruction()))
                        .log("bad payment information")
                                /** {@link PayRoute#configure() next} route builder **/
                        .to(PayEndpoint.BAD_PAYMENT_INFORMATION_ENDPOINT.getInstruction())
                        .otherwise()
                        .log("good payment information")
                                /** {@link ValidateCart#configure() next} route builder **/
                        .to(PayEndpoint.EXTRACT_CART.getInstruction())
                        .endChoice();

        // TODO comments
        from(PayEndpoint.GET_DELIVERY_PRICE.getInstruction())
                .log("Begin of the flow to get the delivery Price")

                        /** {@link HandleDeliveryPrice#process(Exchange)}  **/
                .process(handleDeliveryPrice)

                        /** {@link ProceedPayment#configure()}  **/
                .to(PayEndpoint.SHOP3000_PAYMENT.getInstruction())
        ;

        /**
         * This part of the flow handle the payment of shop3000 with the client payment information
         * For the moment it call a process that mock the payment system.
         */
        from(PayEndpoint.SHOP3000_PAYMENT.getInstruction())
                .log("Starting the flow that will handle the payment")
                /** {@link Payment#process(Exchange)} **/
                .process(payment)
                .log("after payment")
                /** The rest of the flow is below **/
                .removeHeaders("*")
                .wireTap(PayEndpoint.SHOPS_PAYMENT.getInstruction(), true, exchange -> {
                    exchange.getIn().setBody(exchange.getProperty(PayProperties.CLIENT_ID_PROPERTY.getInstruction()));
                })
                /** {@link PayUnmarshaller#configure()} **/
                .to(PayEndpoint.PAYMENT_TO_WS.getInstruction());

        /**
         * This part of the flow will handle the payment of the different shops
         */
        from(PayEndpoint.SHOPS_PAYMENT.getInstruction())
                .log("Begin of the flow that will pay the different shop")
                .multicast()
                    .parallelProcessing()
                    .aggregationStrategy(new BooleanAndAggregationStrategy())
                /** the 3 {@link PayShop#configure() next} flows **/
                    .to(PayEndpoint.PAY_BEER.getInstruction())
                    .to(PayEndpoint.PAY_BIKO.getInstruction())
                    .to(PayEndpoint.PAY_VOLLEY.getInstruction())
                .end();
    }


    /**
     * This process handle the payment of shop3000
     * It will check the price of the delivery and the price of the cart, add the two and pay with the payment information
     * given by the client
     */
    private class Payment implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            double deliveryPrice = (double) exchange.getProperty(PayProperties.DELIVERY_PRICE_PROPERTY.getInstruction());
            double cartPrice = (double) exchange.getProperty(PayProperties.CART_PRICE_PROPERTY.getInstruction());

            double total = deliveryPrice + cartPrice;

            PaymentInformation paymentInformation = objectMapper.readValue(
                    (String) exchange.getProperty(PayProperties.PAYMENT_INFORMATION_PROPERTY.getInstruction()),
                    PaymentInformation.class);

            boolean paymentDone = MockPaymentSystem.pay(paymentInformation.getCardNumber(), paymentInformation.getExpirationDate(),
                    paymentInformation.getSecurityCode(), paymentInformation.getAddress(), total);

            exchange.setProperty(PayProperties.PAYMENT_STATE_PROPERTY.getInstruction(),paymentDone);
        }
    }


    /**
     * This process will add a property where the name is defined in the constant DELIVERY_PRICE_PROPERTY
     * This property will contain the price of the delivery given by the the delivery system.
     */
    private class HandleDeliveryPrice implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentInformation paymentInformation =  objectMapper.readValue(
                    (String) exchange.getProperty(PayProperties.PAYMENT_INFORMATION_PROPERTY.getInstruction()), PaymentInformation.class);
            String clientAddress = paymentInformation.getAddress();

            double price = MockDeliverySystem.getDeliveryPrice(Shop3000Information.ADDRESS, clientAddress);
            exchange.setProperty(PayProperties.DELIVERY_PRICE_PROPERTY.getInstruction(), price);

        }
    }
}

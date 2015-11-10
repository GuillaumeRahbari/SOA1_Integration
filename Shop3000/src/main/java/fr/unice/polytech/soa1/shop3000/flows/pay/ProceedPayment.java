package fr.unice.polytech.soa1.shop3000.flows.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.PaymentInformation;
import fr.unice.polytech.soa1.shop3000.flows.delivery.DeliveryFlow;
import fr.unice.polytech.soa1.shop3000.utils.MockPaymentSystem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes related to payment.
 */
public class ProceedPayment extends RouteBuilder {

    public static String PAYMENT_STATE_PROPERTY = "paymentStatus";

    private Payment payment = new Payment();

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
                .when(exchange -> exchange.getProperty(PayUnmarshaller.PAYMENT_INFORMATION_PROPERTY)
                        .equals(PayUnmarshaller.BAD_INFORMATION))
                .log("bad payment information")
                        /** {@link PayRoute#configure() next} route builder **/
                .to(PayEndpoint.BAD_PAYMENT_INFORMATION_ENDPOINT.getInstruction())
                .otherwise()
                .log("good payment information")
                        /** {@link ValidateCart#configure() next} route builder **/
                .to(PayEndpoint.EXTRACT_CART.getInstruction())
                .endChoice();

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
              //  .wireTap(PayEndpoint.SHOPS_PAYMENT.getInstruction())
               // .to(PayEndpoint.PAYMENT_TO_WS.getInstruction())
        ;


        /**
         * This part of the flow will handle the payment of the different shops
         */
        from(PayEndpoint.SHOPS_PAYMENT.getInstruction())
                .log("Begin of the flow that will pay the different shop")
                // TODO : Ici faut mettre un multicast parrallele pour les 3 shops et appeler leur méthodes de payment avec les ID bancaire de shop3000
                // TODO : Ici c'est la fin du flow gérer les 200 / 400
                ;
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
            double deliveryPrice = (double) exchange.getProperty(DeliveryFlow.DELIVERY_PRICE_PROPERTY);
            double cartPrice = (double) exchange.getProperty(ValidateCart.CART_PRICE_PROPERTY);

            double total = deliveryPrice + cartPrice;

            PaymentInformation paymentInformation = objectMapper.readValue((String) exchange.getProperty(PayUnmarshaller.PAYMENT_INFORMATION_PROPERTY), PaymentInformation.class);
            boolean paymentDone = MockPaymentSystem.pay(paymentInformation.getCardNumber(), paymentInformation.getExpirationDate(), paymentInformation.getSecurityCode(), paymentInformation.getAddress(), total);

            exchange.setProperty(PAYMENT_STATE_PROPERTY,paymentDone);
        }
    }
}

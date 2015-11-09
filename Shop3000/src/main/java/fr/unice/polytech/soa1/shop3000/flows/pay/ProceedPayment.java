package fr.unice.polytech.soa1.shop3000.flows.pay;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 *
 * Builds routes related to payment.
 */
public class ProceedPayment extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /** Flow checking payment information.
         *  Expects a property "paymentInformation" with a JSON representing a PaymentInformation object.
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

        from(PayEndpoint.GET_CART_PRICE.getInstruction())
                .log("Starting the flow that will calculate the price to pay");
        // TODO : Appeler le bean qui renvoie le prix pour une livraison

    }

}

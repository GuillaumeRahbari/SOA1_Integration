package fr.unice.polytech.soa1.shop3000.flows.pay;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author Marc Karassev
 */
public class ValidateCartAndPayment extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /** Flow checking payment information.
         *  Expects a property "paymentInformation" with a JSON representing a PaymentInformation object.
         */
        from(Endpoint.VALIDATE_PAYMENT_INFORMATION.getInstruction())
                .log("starting payment information checking")
                .choice()/*
                    .when(new Predicate() {
                        public boolean matches(Exchange exchange) {
                            return exchange.getProperty(PayRoute.PAYMENT_INFORMATION_PROPERTY)
                                    .equals(JsonPaymentInformationExtractor.BAD_INFORMATION);
                        }
                    })
                        .log(JsonPaymentInformationExtractor.BAD_INFORMATION)
                        // TODO send message
                    .otherwise()
                        .log("good information")
                        .to(Endpoint.VALIDATE_CART.getInstruction())*/
                .endChoice();

        from(Endpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
                .log("body: ${body}")
                // TODO extract payment info from body and set a property
                /*
                .multicast()
                    .aggregationStrategy(new BooleanAndAggregationStrategy())
                    .log("multicasting")
                    .to(Endpoint.ADD_TO_CART_ALL_HAIL_BEER.getInstruction())
                    .to(Endpoint.ADD_TO_CART_BIKO.getInstruction())
                    .to(Endpoint.ADD_TO_CART_VOLLEY_ON_THE_BEACH.getInstruction())
                .log("merging")
                .end()
                */
                .log("body: ${body}");
                // TODO extract payment info from property and set body
                //.to(Endpoint.PAY.getInstruction());
    }
}

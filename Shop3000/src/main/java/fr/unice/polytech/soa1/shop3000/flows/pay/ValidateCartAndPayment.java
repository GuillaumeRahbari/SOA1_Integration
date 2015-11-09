package fr.unice.polytech.soa1.shop3000.flows.pay;

import fr.unice.polytech.soa1.shop3000.flows.JoinAggregationStrategy;
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
                .choice()
                    .when(new Predicate() {
                        public boolean matches(Exchange exchange) {
                            return exchange.getProperty(PayRoute.PAYMENT_INFORMATION_PROPERTY)
                                    .equals(JsonPaymentInformationExtractor.BAD_INFORMATION);
                        }
                    })
                        .log("bad information")
                        // TODO send message
                    .otherwise()
                        .log("good information")
                        .to(Endpoint.VALIDATE_CART.getInstruction())
                .endChoice();

        from(Endpoint.VALIDATE_CART.getInstruction())
                .log("starting cart validation")
                .log("body: ${body}")
                // TODO extract payment info from body and set a property
                .multicast()
                    .aggregationStrategy(new JoinAggregationStrategy()) // TODO c'etait une autre strat d'aggreg
                    .log("multicasting")
                    .to(Endpoint.CHECK_CLIENT_BEER.getInstruction())
                  //  .to(Endpoint.CHECK_CLIENT_BIKO.getInstruction())
                  //  .to(Endpoint.CHECK_CLIENT_VOLLEY.getInstruction())
                .log("merging")
                .end()
                .log("body: ${body}");
                // TODO extract payment info from property and set body
                //.to(Endpoint.PAY.getInstruction());
    }
}

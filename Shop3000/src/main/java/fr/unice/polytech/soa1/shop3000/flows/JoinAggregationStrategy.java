package fr.unice.polytech.soa1.shop3000.flows;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * Created by Quentin on 10/21/2015.
 */
public class JoinAggregationStrategy implements AggregationStrategy {


    public Exchange aggregate(Exchange exchange, Exchange exchange1) {
        if (exchange == null) {
            return exchange1;
        } else {
            String body1 = exchange.getIn().getBody(String.class);
            String body2 = exchange1.getIn().getBody(String.class);
            String merged = (body1 == null) ? body2 : body1 + "," + body2;
            exchange.getIn().setBody(merged);
            return exchange;
        }
    }
}

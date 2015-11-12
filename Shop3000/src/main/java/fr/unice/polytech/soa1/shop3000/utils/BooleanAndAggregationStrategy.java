package fr.unice.polytech.soa1.shop3000.utils;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * @author Marc Karassev
 */
public class BooleanAndAggregationStrategy implements AggregationStrategy {

    public Exchange aggregate(Exchange exchange, Exchange exchange1) {
        if(exchange == null) {
            return exchange1;
        } else {

            boolean bool = exchange.getIn().getBody(Boolean.class),
                    bool1 = exchange1.getIn().getBody(Boolean.class);

            exchange.getIn().setBody(bool && bool1);
            return exchange;
        }
    }
}

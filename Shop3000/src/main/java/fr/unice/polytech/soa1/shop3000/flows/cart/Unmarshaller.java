package fr.unice.polytech.soa1.shop3000.flows.cart;

import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

import java.util.Map;

/**
 * Created by guillaume on 02/11/2015.
 */
public class Unmarshaller extends RouteBuilder {



    @Override
    public void configure() throws Exception {

        from(Endpoint.UNMARSHALL_JSON_ITEM.getInstruction())
                .to(Endpoint.ADD_ITEM_CART.getInstruction());


        from(Endpoint.CSV_INPUT_DIRECTORY.getInstruction())
                .log("Handling a csv File : ${file:name}")
                .log("Unmarshalling")
                .unmarshal(buildCsvFormat())
                .log("Splitting")
                .split(body())
                .log("processing")
                .process(new CsvToCartItemProcessor())
                .to(Endpoint.ADD_ITEM_CART.getInstruction());

    }

    // transform a CSV file delimited by commas, skipping the headers and producing a Map as output
    private static CsvDataFormat buildCsvFormat() {
        CsvDataFormat format = new CsvDataFormat();
        format.setDelimiter(",");
        format.setSkipHeaderRecord(true);
        format.setUseMaps(true);
        return format;
    }
}

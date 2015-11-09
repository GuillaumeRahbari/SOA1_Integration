package fr.unice.polytech.soa1.shop3000.flows.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.shop3000.business.CatalogItem;
import fr.unice.polytech.soa1.shop3000.utils.Endpoint;
import fr.unice.polytech.soa1.shop3000.utils.SuperProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

/**
 * Created by guillaume on 02/11/2015.
 */
public class Unmarshaller extends RouteBuilder {

    private JsonToItem jsonUnmarshaller;
    private CsvToCartItemProcessor csvToCartItemProcessor;

    public Unmarshaller () {
        jsonUnmarshaller = new JsonToItem();
        csvToCartItemProcessor = new CsvToCartItemProcessor();
    }

    @Override
    public void configure() throws Exception {

        /**
         * This route is here to unmarshall the json before go to the business layer.
         * We do this with the process JsonUnmarshaller.
         * The flow come from {@link CartRoute}
         */
        from(Endpoint.UNMARSHALL_JSON_ITEM.getInstruction())
                .log("DEbut du process")
                /** {@link JsonToItem} **/
                .process(jsonUnmarshaller)

                /** It redirects to {@link CartFlows} **/
                 .to(Endpoint.ADD_ITEM_CART.getInstruction());


        /**
         * This route is here to unmarshall the csv file before go to the business layer.
         * We do this with the process CsvToCartItemProcessor.
         * It redirects to the ADD_ITEM_CART endpoint.
         *
         * TODO : Sortir la méthode private BuildCsvFormat car elle peut être utilise ailleur
         */
        from(Endpoint.CSV_INPUT_DIRECTORY.getInstruction())
                .log("Handling a csv File : ${file:name}")
                .log("Unmarshalling")
                .unmarshal(buildCsvFormat())
                .log("Splitting")
                .split(body())
                .log("processing")
                .process(csvToCartItemProcessor)
                .to(Endpoint.ADD_ITEM_CART.getInstruction());
    }

    /**
     * Function used to create a CsvDataFormat matching with the files given in input.
     *
     * @return a format on CSV file delimited by commas, skipping the headers and producing a Map as output
     */
    private static CsvDataFormat buildCsvFormat() {
        CsvDataFormat format = new CsvDataFormat();
        format.setDelimiter(",");
        format.setSkipHeaderRecord(true);
        format.setUseMaps(true);
        return format;
    }


    private class JsonToItem extends SuperProcessor {

        /**
         * This process unmarshalls the json body and creates a property item with the object.
         * @param exchange This Exchange object has a body containing a CatalogItem.
         * @throws Exception
         */
        @Override
        public void process(Exchange exchange) throws Exception {
            // Read the exchange body.
            String body = extractExchangeBody(exchange);
            // Mapping of the body into the object CatalogItem.
            ObjectMapper mapper = new ObjectMapper();
            CatalogItem catalogItem = mapper.readValue(body, CatalogItem.class);
            // Set of the item property with the CatalogItem object
            exchange.setProperty("item", catalogItem);
        }
    }


}

package fr.unice.polytech.soa1.shop3000.utils;

/**
 * @author Quentin Cornevin
 *
 * This interface will contain all the endpoint of the application
 */
public interface Endpoints {

    String CLIENT_FILE_INPUT = "file:camel/input";
    String CLIENT_DATABASE = "direct:checkClient";
}

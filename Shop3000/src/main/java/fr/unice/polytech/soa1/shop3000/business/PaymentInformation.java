package fr.unice.polytech.soa1.shop3000.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marc Karassev
 *
 * Class representing payment information given at payment time.
 * The related JSON reprensentation is the following:
 * {
 *     "cardNumber": string,
 *     "expirationDate": string,
 *     "securityCode": string,
 *     "address": string
 * }
 * An example is:
 * {
 *     "cardNumber": "0000111122223333",
 *     "expirationDate": "01/01",
 *     "securityCode": "123",
 *     "address": "somewhere"
 * }
 */
public class PaymentInformation {

    // Attributes

    private String cardNumber, expirationDate, securityCode, address;

    // Constructors

    @JsonCreator
    public PaymentInformation(@JsonProperty(value = "cardNumber", required = true) String cardNumber,
                              @JsonProperty(value = "expirationDate", required = true) String expirationDate,
                              @JsonProperty(value = "securityCode", required = true) String securityCode,
                              @JsonProperty(value = "address", required = true) String address) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.address = address;
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + cardNumber + ", " + expirationDate + ", " + securityCode + " }";
    }

    // Getters and setters

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getAddress() {
        return address;
    }

}

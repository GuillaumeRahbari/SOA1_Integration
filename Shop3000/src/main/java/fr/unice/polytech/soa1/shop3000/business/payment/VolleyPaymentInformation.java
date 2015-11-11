package fr.unice.polytech.soa1.shop3000.business.payment;

/**
 * @author Marc Karassev
 *
 * Payment information for Volley on the Beach payment service.
 * Has the following JSON representation:
 * {
 *     "cardNumber": number,
 *     "validityDate": string,
 *     "crypto": number,
 *     "address": string
 * }
 */
public class VolleyPaymentInformation {

    // Attributes

    private long cardNumber;
    private String validityDate, address;
    private short crypto;

    // Constructors

    public VolleyPaymentInformation(PaymentInformation information) {
        this.cardNumber = Long.parseLong(information.getCardNumber());
        this.validityDate = information.getExpirationDate();
        this.crypto = Short.parseShort(information.getSecurityCode());
        this.address = information.getAddress();
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + cardNumber + ", " + validityDate + ", " + crypto + ", " + address + " }";
    }

    // Getters and setters

    public long getCardNumber() {
        return cardNumber;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public String getAddress() {
        return address;
    }

    public short getCrypto() {
        return crypto;
    }
}

package fr.unice.polytech.soa1.shop3000.business.payment;

/**
 * @author Marc Karassev
 *
 * Payment information for Biko payment service.
 * Has the following JSON representation:
 * {
 *     "creditCardNumber": number,
 *     "expirationDate": number,
 *     "securityCode": number
 * }
 */
public class BikoPaymentInformation {

    // Attributes

    private int creditCardNumber, expirationDate, securityCode;

    // Constructors

    public BikoPaymentInformation(PaymentInformation paymentInformation) {
        creditCardNumber = Integer.parseInt(paymentInformation.getCardNumber());
        expirationDate = Integer.parseInt(paymentInformation.getExpirationDate());
        securityCode = Integer.parseInt(paymentInformation.getExpirationDate());
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + creditCardNumber + ", " + expirationDate + ", " + securityCode + " }";
    }

    // Getters and setters

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public int getSecurityCode() {
        return securityCode;
    }
}

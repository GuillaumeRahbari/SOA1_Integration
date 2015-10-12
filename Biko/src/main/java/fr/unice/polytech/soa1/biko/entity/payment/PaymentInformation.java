package fr.unice.polytech.soa1.biko.entity.payment;

/**
 * Created by Quentin on 10/5/2015.
 */
public class PaymentInformation {

    private int securityCode;
    private int creditCardNumber;
    private int expirationDate;

    /**
     * Create a client with a given id and a given name.
     *
     * @param id of the client created
     * @param name of the client created
     */
    public PaymentInformation(int securityCode, int creditCardNumber, int expirationDate) {
        this.securityCode = securityCode;
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
    }

    /**
     * Create a default client with 0 as id and default as name;
     */
    public PaymentInformation() {
        this(0,0,0);
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }
}

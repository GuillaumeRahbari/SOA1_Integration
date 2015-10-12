package fr.unice.polytech.soa1.biko.entity.payment;

/**
 * Created by user on 05/10/2015.
 */
public class Payment {

    private long orderId;
    private int expirationDate;
    private int securityCode;
    private int creditCardNumber;
    private PaymentStatus paymentStatus;

    public Payment(long orderId, int expirationDate, int securityCode, int creditCardNumber, PaymentStatus paymentStatus) {
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }

    public Payment(PaymentInformation paymentInformation, long orderId) {
        this(orderId, paymentInformation.getExpirationDate(),paymentInformation.getSecurityCode(),paymentInformation.getCreditCardNumber(), PaymentStatus.TODO);
    }


    public Payment() {
        this(0,0,0,0,PaymentStatus.TODO);
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

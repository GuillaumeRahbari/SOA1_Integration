package fr.unice.polytech.soa1.shop3000.utils;

/***
 * This class mock the system of payment of shop3000
 *
 * Created by Quentin on 11/10/2015.
 */
public class MockPaymentSystem {

    /**
     * This method will return if the payment is done or not.
     * @param cardNumber
     * @param expirationDate
     * @param securityCode
     * @param address
     * @return true if the payment is done, false otherwise.
     */
    public static boolean pay(String cardNumber, String expirationDate, String securityCode, String address, double amount) {
        double random = Math.random();
        if(Long.valueOf(cardNumber) % 2 == 0) {
            return true;
        }
        return false;
    }
}

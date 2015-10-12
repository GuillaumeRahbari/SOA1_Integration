package fr.unice.polytech.soa1.biko.entity.payment;

import fr.unice.polytech.soa1.biko.entity.OrderStorage;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.OrderStatus;
import fr.unice.polytech.soa1.biko.entity.orderSpecification.OrderStatusWrapper;

import java.util.*;

/**
 * Created by user on 05/10/2015.
 */
public class PaymentStorage {
    private static Map<Long,Payment> payments = new HashMap<>();

    public static void add(Payment p){
        payments.put(p.getOrderId(), p);
    }

    public static ArrayList<Payment> getPayments(){
        Collection<Payment> paymentCollection = payments.values();
        return new ArrayList<>(paymentCollection);
    }

    public static void updateFailedPayment(FailedPayment failedPayment) {
        List<Payment> failed = failedPayment.getFailedPayment();
        for(Payment p : failed) {
            payments.get(p.getOrderId()).setPaymentStatus(PaymentStatus.FAILED);
        }
    }


    public static void updateSucceedPayment(SucceedPayment succeedPayment) {
        List<Payment> succeed = succeedPayment.getSucceedPayment();
        for(Payment p : succeed) {
            payments.get(p.getOrderId()).setPaymentStatus(PaymentStatus.SUCCEEDED);
            OrderStorage.read(p.getOrderId()).setStatus(new OrderStatusWrapper(OrderStatus.IN_PREPARATION));
        }
    }

    static {
        payments.put(12345L, new Payment(12345L, 1, 1, 1, PaymentStatus.TODO));
        payments.put(12325L, new Payment(12325L, 2, 2, 2, PaymentStatus.TODO));
    }
}

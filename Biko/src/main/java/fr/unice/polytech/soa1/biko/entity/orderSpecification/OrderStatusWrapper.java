package fr.unice.polytech.soa1.biko.entity.orderSpecification;

/**
 * Created by Quentin on 10/5/2015.
 */
public class OrderStatusWrapper {

    OrderStatus status;

    public OrderStatusWrapper() {
        this.status = OrderStatus.AWAITING_PAYMENT;
    }

    public OrderStatusWrapper(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getOrderStatus() {
        return status;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}

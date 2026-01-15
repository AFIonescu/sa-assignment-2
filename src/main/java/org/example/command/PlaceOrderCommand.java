package org.example.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Order;
import org.example.handler.OrderValidationHandler;
import org.example.notification.NotificationService;
import org.example.payment.PaymentStrategy;
import org.example.repository.OrderRepository;

@Slf4j
@RequiredArgsConstructor
public class PlaceOrderCommand implements OrderCommand {
    private final Order order;
    private final OrderValidationHandler validationHandler;
    private final PaymentStrategy paymentStrategy;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;

    @Override
    public void execute() {
        log.info("PlaceOrderCommand: Placing order for customer: {}", order.getCustomerName());

        if (!validationHandler.validate(order)) {
            order.updateStatus("FAILED");
            orderRepository.save(order);
            notificationService.notifyObservers("Order " + order.getId() + " failed validation");
            return;
        }

        paymentStrategy.pay(order.getTotalAmount());
        order.updateStatus("CONFIRMED");
        orderRepository.save(order);

        log.info("PlaceOrderCommand: Order placed successfully - ID: {}", order.getId());
        notificationService.notifyObservers("Order " + order.getId() + " confirmed");
    }
}

package com.sa.ecommerce.command;

import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.observer.NotificationService;
import com.sa.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Concrete Command for canceling an order
 * Command Pattern implementation
 */
@Slf4j
@RequiredArgsConstructor
public class CancelOrderCommand implements OrderCommand {

    private final Order order;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;

    @Override
    public boolean execute() {
        log.info("CancelOrderCommand: Executing cancel order command for order ID: {}", order.getId());

        try {
            // Check if order can be cancelled
            if (order.getStatus() == OrderStatus.CANCELLED) {
                log.warn("CancelOrderCommand: Order {} is already cancelled", order.getId());
                return false;
            }

            if (order.getStatus() == OrderStatus.CONFIRMED) {
                log.warn("CancelOrderCommand: Order {} is already confirmed, cancellation may require refund", order.getId());
                // In real scenario, trigger refund process
            }

            // Cancel the order
            order.setStatus(OrderStatus.CANCELLED);
            log.info("CancelOrderCommand: Order {} cancelled successfully", order.getId());

            // Save cancelled order to database
            orderRepository.save(order);

            // Notify observers
            notificationService.notifyObservers(order);

            return true;

        } catch (Exception e) {
            log.error("CancelOrderCommand: Error executing cancel order command", e);
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Cancel Order Command for order ID: " + order.getId();
    }
}

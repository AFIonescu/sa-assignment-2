package com.sa.ecommerce.command;

import com.sa.ecommerce.chain.ValidationHandler;
import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.observer.NotificationService;
import com.sa.ecommerce.repository.OrderRepository;
import com.sa.ecommerce.strategy.PaymentStrategy;
import com.sa.ecommerce.strategy.PaymentStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Concrete Command for placing an order
 * Command Pattern implementation
 */
@Slf4j
@RequiredArgsConstructor
public class PlaceOrderCommand implements OrderCommand {

    private final Order order;
    private final ValidationHandler validationChain;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;

    @Override
    public boolean execute() {
        log.info("PlaceOrderCommand: Executing place order command for customer: {}", order.getCustomerName());

        try {
            // Step 1: Validate order using Chain of Responsibility
            log.debug("PlaceOrderCommand: Starting validation chain");
            if (!validationChain.handle(order)) {
                order.setStatus(OrderStatus.FAILED);
                log.error("PlaceOrderCommand: Order validation failed");
                // TODO: Save failed order to database
                orderRepository.save(order);
                notificationService.notifyObservers(order);
                return false;
            }

            order.setStatus(OrderStatus.VALIDATED);
            log.info("PlaceOrderCommand: Order validated successfully");

            // Step 2: Process payment using Strategy Pattern
            log.debug("PlaceOrderCommand: Processing payment");
            order.setStatus(OrderStatus.PAYMENT_PROCESSING);
            orderRepository.save(order);

            PaymentStrategy paymentStrategy = paymentStrategyFactory.getStrategy(order.getPaymentMethod());
            if (!paymentStrategy.processPayment(order)) {
                order.setStatus(OrderStatus.FAILED);
                log.error("PlaceOrderCommand: Payment processing failed");
                // TODO: Save failed order to database
                orderRepository.save(order);
                notificationService.notifyObservers(order);
                return false;
            }

            // Step 3: Confirm order
            order.setStatus(OrderStatus.CONFIRMED);
            log.info("PlaceOrderCommand: Order confirmed successfully - ID: {}", order.getId());

            // TODO: Save confirmed order to database
            orderRepository.save(order);

            // Step 4: Notify observers using Observer Pattern
            notificationService.notifyObservers(order);

            return true;

        } catch (Exception e) {
            log.error("PlaceOrderCommand: Error executing place order command", e);
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            notificationService.notifyObservers(order);
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Place Order Command for customer: " + order.getCustomerName();
    }
}

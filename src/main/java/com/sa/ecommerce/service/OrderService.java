package com.sa.ecommerce.service;

import com.sa.ecommerce.chain.*;
import com.sa.ecommerce.command.CancelOrderCommand;
import com.sa.ecommerce.command.OrderCommand;
import com.sa.ecommerce.command.PlaceOrderCommand;
import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.observer.EmailNotification;
import com.sa.ecommerce.observer.NotificationService;
import com.sa.ecommerce.observer.SMSNotification;
import com.sa.ecommerce.repository.OrderRepository;
import com.sa.ecommerce.strategy.PaymentStrategyFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for order business logic
 * Coordinates all design patterns
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final NotificationService notificationService;

    // Chain of Responsibility handlers
    private final CustomerValidationHandler customerValidationHandler;
    private final InventoryCheckHandler inventoryCheckHandler;
    private final PaymentValidationHandler paymentValidationHandler;

    // Observers
    private final EmailNotification emailNotification;
    private final SMSNotification smsNotification;

    /**
     * Initialize the validation chain and register observers
     */
    @PostConstruct
    public void init() {
        log.info("OrderService: Initializing validation chain and observers");

        // Set up Chain of Responsibility
        customerValidationHandler.setNext(inventoryCheckHandler);
        inventoryCheckHandler.setNext(paymentValidationHandler);

        // Register observers
        notificationService.addObserver(emailNotification);
        notificationService.addObserver(smsNotification);

        log.info("OrderService: Initialization complete");
    }

    /**
     * Place a new order using Command Pattern
     * @param order Order to place
     * @return Saved order
     */
    @Transactional
    public Order placeOrder(Order order) {
        log.info("OrderService: Placing order for customer: {}", order.getCustomerName());

        OrderCommand placeOrderCommand = new PlaceOrderCommand(
                order,
                customerValidationHandler,
                paymentStrategyFactory,
                notificationService,
                orderRepository
        );

        boolean success = placeOrderCommand.execute();

        if (success) {
            log.info("OrderService: Order placed successfully - ID: {}", order.getId());
        } else {
            log.error("OrderService: Failed to place order for customer: {}", order.getCustomerName());
        }

        return order;
    }

    /**
     * Cancel an existing order using Command Pattern
     * @param orderId Order ID to cancel
     * @return Cancelled order or empty if not found
     */
    @Transactional
    public Optional<Order> cancelOrder(Long orderId) {
        log.info("OrderService: Cancelling order ID: {}", orderId);

        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty()) {
            log.warn("OrderService: Order not found with ID: {}", orderId);
            return Optional.empty();
        }

        Order order = orderOptional.get();
        OrderCommand cancelOrderCommand = new CancelOrderCommand(
                order,
                notificationService,
                orderRepository
        );

        boolean success = cancelOrderCommand.execute();

        if (success) {
            log.info("OrderService: Order cancelled successfully - ID: {}", orderId);
        } else {
            log.error("OrderService: Failed to cancel order ID: {}", orderId);
        }

        return Optional.of(order);
    }

    /**
     * Get all orders
     * @return List of all orders
     */
    public List<Order> getAllOrders() {
        log.debug("OrderService: Fetching all orders");
        return orderRepository.findAll();
    }

    /**
     * Get order by ID
     * @param id Order ID
     * @return Order if found
     */
    public Optional<Order> getOrderById(Long id) {
        log.debug("OrderService: Fetching order with ID: {}", id);
        return orderRepository.findById(id);
    }

    /**
     * Get orders by status
     * @param status Order status
     * @return List of orders with given status
     */
    public List<Order> getOrdersByStatus(OrderStatus status) {
        log.debug("OrderService: Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status);
    }

    /**
     * Get orders by customer email
     * @param email Customer email
     * @return List of customer orders
     */
    public List<Order> getOrdersByCustomerEmail(String email) {
        log.debug("OrderService: Fetching orders for customer email: {}", email);
        return orderRepository.findByCustomerEmail(email);
    }
}

package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Order;
import org.example.command.OrderCommand;
import org.example.command.PlaceOrderCommand;
import org.example.handler.InventoryCheckHandler;
import org.example.handler.PaymentValidationHandler;
import org.example.notification.EmailNotification;
import org.example.notification.NotificationService;
import org.example.notification.SMSNotification;
import org.example.payment.CreditCardPayment;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final NotificationService notificationService;
    private final InventoryCheckHandler inventoryCheckHandler;
    private final PaymentValidationHandler paymentValidationHandler;
    private final CreditCardPayment creditCardPayment;
    private final EmailNotification emailNotification;
    private final SMSNotification smsNotification;

    @PostConstruct
    public void init() {
        log.info("OrderService: Initializing validation chain and observers");

        inventoryCheckHandler.setNext(paymentValidationHandler);

        notificationService.addObserver(emailNotification);
        notificationService.addObserver(smsNotification);

        log.info("OrderService: Initialization complete");

        // Create sample orders for demonstration
        log.info("OrderService: Creating sample orders");
        createSampleOrders();
    }

    private void createSampleOrders() {
        Order order1 = new Order(null, "Alice Johnson", "PENDING", 299.99);
        placeOrder(order1);

        Order order2 = new Order(null, "Bob Smith", "PENDING", 149.50);
        placeOrder(order2);

        Order order3 = new Order(null, "Carol Williams", "PENDING", 599.00);
        placeOrder(order3);

        log.info("OrderService: Sample orders created successfully");
    }

    public Order placeOrder(Order order) {
        log.info("OrderService: Placing order for customer: {}", order.getCustomerName());

        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);

        OrderCommand placeOrderCommand = new PlaceOrderCommand(
                savedOrder,
                inventoryCheckHandler,
                creditCardPayment,
                notificationService,
                orderRepository
        );

        placeOrderCommand.execute();

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        log.debug("OrderService: Fetching all orders");
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        log.debug("OrderService: Fetching order with ID: {}", id);
        return orderRepository.findById(id);
    }
}

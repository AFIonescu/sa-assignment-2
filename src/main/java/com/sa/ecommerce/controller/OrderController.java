package com.sa.ecommerce.controller;

import com.sa.ecommerce.dto.CreateOrderRequest;
import com.sa.ecommerce.dto.OrderResponse;
import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for order management
 * Presentation Layer in Spring Boot architecture
 */
@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Place a new order
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("OrderController: Received request to place order for customer: {}", request.getCustomerName());

        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .totalAmount(request.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentDetails(request.getPaymentDetails())
                .build();

        Order placedOrder = orderService.placeOrder(order);

        HttpStatus status = placedOrder.getStatus() == OrderStatus.CONFIRMED
                ? HttpStatus.CREATED
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(OrderResponse.fromOrder(placedOrder));
    }

    /**
     * Get all orders
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("OrderController: Fetching all orders");

        List<OrderResponse> orders = orderService.getAllOrders().stream()
                .map(OrderResponse::fromOrder)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    /**
     * Get order by ID
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        log.info("OrderController: Fetching order with ID: {}", id);

        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(OrderResponse.fromOrder(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cancel an order
     * DELETE /api/orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        log.info("OrderController: Received request to cancel order ID: {}", id);

        return orderService.cancelOrder(id)
                .map(order -> ResponseEntity.ok(OrderResponse.fromOrder(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get orders by status
     * GET /api/orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus status) {
        log.info("OrderController: Fetching orders with status: {}", status);

        List<OrderResponse> orders = orderService.getOrdersByStatus(status).stream()
                .map(OrderResponse::fromOrder)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by customer email
     * GET /api/orders/customer/{email}
     */
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerEmail(@PathVariable String email) {
        log.info("OrderController: Fetching orders for customer: {}", email);

        List<OrderResponse> orders = orderService.getOrdersByCustomerEmail(email).stream()
                .map(OrderResponse::fromOrder)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }
}

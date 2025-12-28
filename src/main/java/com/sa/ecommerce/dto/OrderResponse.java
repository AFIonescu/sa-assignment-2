package com.sa.ecommerce.dto;

import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String customerName;
    private String customerEmail;
    private String productName;
    private Integer quantity;
    private Double totalAmount;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse fromOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}

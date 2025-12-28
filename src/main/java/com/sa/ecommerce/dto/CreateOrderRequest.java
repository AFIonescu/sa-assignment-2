package com.sa.ecommerce.dto;

import com.sa.ecommerce.model.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    private Double totalAmount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Payment details are required")
    private String paymentDetails;
}

package com.sa.ecommerce.strategy;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Concrete Strategy for PayPal payments
 * Strategy Pattern implementation
 */
@Slf4j
@Component
public class PayPalPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(Order order) {
        log.debug("PayPalPayment: Processing PayPal payment for order ID: {}", order.getId());

        // TODO: Integrate with PayPal API
        // Simulating PayPal processing
        String paypalEmail = order.getPaymentDetails();

        if (paypalEmail == null || !paypalEmail.contains("@")) {
            log.error("PayPalPayment: Invalid PayPal email");
            return false;
        }

        // Simulate payment processing
        log.info("PayPalPayment: Charging ${} to PayPal account: {}",
                 order.getTotalAmount(),
                 paypalEmail);

        return true;
    }

    @Override
    public String getStrategyName() {
        return "PayPal Payment";
    }
}

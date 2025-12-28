package com.sa.ecommerce.strategy;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Concrete Strategy for Credit Card payments
 * Strategy Pattern implementation
 */
@Slf4j
@Component
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(Order order) {
        log.debug("CreditCardPayment: Processing credit card payment for order ID: {}", order.getId());

        // TODO: Integrate with actual payment gateway
        // Simulating credit card processing
        String cardNumber = order.getPaymentDetails();

        if (cardNumber == null || cardNumber.length() < 13) {
            log.error("CreditCardPayment: Invalid card number");
            return false;
        }

        // Simulate payment processing
        log.info("CreditCardPayment: Charging ${} to credit card ending in {}",
                 order.getTotalAmount(),
                 cardNumber.substring(cardNumber.length() - 4));

        return true;
    }

    @Override
    public String getStrategyName() {
        return "Credit Card Payment";
    }
}

package com.sa.ecommerce.strategy;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Concrete Strategy for Cryptocurrency payments
 * Strategy Pattern implementation
 */
@Slf4j
@Component
public class CryptocurrencyPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(Order order) {
        log.debug("CryptocurrencyPayment: Processing cryptocurrency payment for order ID: {}", order.getId());

        // TODO: Integrate with blockchain/crypto payment gateway
        // Simulating cryptocurrency processing
        String walletAddress = order.getPaymentDetails();

        if (walletAddress == null || walletAddress.length() < 26) {
            log.error("CryptocurrencyPayment: Invalid wallet address");
            return false;
        }

        // Simulate payment processing
        log.info("CryptocurrencyPayment: Charging ${} to wallet: {}",
                 order.getTotalAmount(),
                 walletAddress.substring(0, 10) + "...");

        return true;
    }

    @Override
    public String getStrategyName() {
        return "Cryptocurrency Payment";
    }
}

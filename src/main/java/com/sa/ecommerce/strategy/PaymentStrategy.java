package com.sa.ecommerce.strategy;

import com.sa.ecommerce.model.Order;

/**
 * Strategy Pattern interface for payment processing
 * Allows different payment methods to be used interchangeably
 */
public interface PaymentStrategy {

    /**
     * Process payment for an order
     * @param order Order to process payment for
     * @return true if payment successful, false otherwise
     */
    boolean processPayment(Order order);

    /**
     * Get the name of the payment strategy
     * @return Strategy name
     */
    String getStrategyName();
}

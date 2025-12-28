package com.sa.ecommerce.chain;

import com.sa.ecommerce.model.Order;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class for Chain of Responsibility pattern
 * Each handler can process the order or pass it to the next handler
 */
@Slf4j
@Setter
public abstract class ValidationHandler {

    protected ValidationHandler next;

    /**
     * Handle the validation request
     * @param order Order to validate
     * @return true if validation passes, false otherwise
     */
    public boolean handle(Order order) {
        if (!validate(order)) {
            return false;
        }

        if (next != null) {
            return next.handle(order);
        }

        return true;
    }

    /**
     * Perform specific validation logic
     * @param order Order to validate
     * @return true if validation passes, false otherwise
     */
    protected abstract boolean validate(Order order);
}

package com.sa.ecommerce.chain;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler to validate payment details
 * Chain of Responsibility Pattern implementation
 */
@Slf4j
@Component
public class PaymentValidationHandler extends ValidationHandler {

    @Override
    protected boolean validate(Order order) {
        log.debug("PaymentValidationHandler: Validating payment for order");

        // Validate payment method is set
        if (order.getPaymentMethod() == null) {
            log.error("PaymentValidationHandler: Payment method is null");
            return false;
        }

        // Validate payment details exist
        if (order.getPaymentDetails() == null || order.getPaymentDetails().trim().isEmpty()) {
            log.error("PaymentValidationHandler: Payment details are missing");
            return false;
        }

        // Validate total amount
        if (order.getTotalAmount() == null || order.getTotalAmount() <= 0) {
            log.error("PaymentValidationHandler: Invalid total amount: {}", order.getTotalAmount());
            return false;
        }

        log.info("PaymentValidationHandler: Payment validation passed");
        return true;
    }
}

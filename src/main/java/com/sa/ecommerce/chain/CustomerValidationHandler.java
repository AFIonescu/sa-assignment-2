package com.sa.ecommerce.chain;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler to validate customer information
 * Chain of Responsibility Pattern implementation
 */
@Slf4j
@Component
public class CustomerValidationHandler extends ValidationHandler {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    protected boolean validate(Order order) {
        log.debug("CustomerValidationHandler: Validating customer information");

        // Validate customer name
        if (order.getCustomerName() == null || order.getCustomerName().trim().isEmpty()) {
            log.error("CustomerValidationHandler: Customer name is missing");
            return false;
        }

        // Validate customer email
        if (order.getCustomerEmail() == null || !order.getCustomerEmail().matches(EMAIL_REGEX)) {
            log.error("CustomerValidationHandler: Invalid customer email: {}", order.getCustomerEmail());
            return false;
        }

        log.info("CustomerValidationHandler: Customer validation passed for: {}", order.getCustomerName());
        return true;
    }
}

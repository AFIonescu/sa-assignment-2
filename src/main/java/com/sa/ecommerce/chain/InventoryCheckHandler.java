package com.sa.ecommerce.chain;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handler to check inventory availability
 * Chain of Responsibility Pattern implementation
 */
@Slf4j
@Component
public class InventoryCheckHandler extends ValidationHandler {

    // Simulated inventory
    private static final int MAX_INVENTORY = 100;

    @Override
    protected boolean validate(Order order) {
        log.debug("InventoryCheckHandler: Checking inventory for product: {}", order.getProductName());

        // TODO: In real scenario, check actual inventory from database
        if (order.getQuantity() <= 0) {
            log.error("InventoryCheckHandler: Invalid quantity: {}", order.getQuantity());
            return false;
        }

        if (order.getQuantity() > MAX_INVENTORY) {
            log.error("InventoryCheckHandler: Quantity {} exceeds available inventory", order.getQuantity());
            return false;
        }

        log.info("InventoryCheckHandler: Inventory check passed for product: {}", order.getProductName());
        return true;
    }
}

package com.sa.ecommerce.observer;

import com.sa.ecommerce.model.Order;

/**
 * Observer Pattern interface
 * Observers are notified when order status changes
 */
public interface OrderObserver {

    /**
     * Update method called when order status changes
     * @param order Updated order
     */
    void update(Order order);
}

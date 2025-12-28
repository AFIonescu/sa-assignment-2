package com.sa.ecommerce.observer;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject in Observer Pattern
 * Manages observers and notifies them of order updates
 */
@Slf4j
@Service
public class NotificationService {

    private final List<OrderObserver> observers = new ArrayList<>();

    /**
     * Register an observer
     * @param observer Observer to register
     */
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
        log.debug("NotificationService: Observer added - {}", observer.getClass().getSimpleName());
    }

    /**
     * Remove an observer
     * @param observer Observer to remove
     */
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
        log.debug("NotificationService: Observer removed - {}", observer.getClass().getSimpleName());
    }

    /**
     * Notify all observers of order update
     * @param order Updated order
     */
    public void notifyObservers(Order order) {
        log.info("NotificationService: Notifying {} observers for order ID: {}",
                 observers.size(),
                 order.getId());

        for (OrderObserver observer : observers) {
            try {
                observer.update(order);
            } catch (Exception e) {
                log.error("NotificationService: Error notifying observer {}: {}",
                         observer.getClass().getSimpleName(),
                         e.getMessage());
            }
        }
    }
}

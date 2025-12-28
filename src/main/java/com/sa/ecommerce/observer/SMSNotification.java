package com.sa.ecommerce.observer;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Concrete Observer for SMS notifications
 * Observer Pattern implementation
 */
@Slf4j
@Component
public class SMSNotification implements OrderObserver {

    @Override
    public void update(Order order) {
        log.debug("SMSNotification: Sending SMS notification for order ID: {}", order.getId());

        // TODO: Integrate with actual SMS service (e.g., Twilio, AWS SNS)
        String smsMessage = buildSMSMessage(order);

        log.info("SMSNotification: SMS sent for order {} - Status: {}",
                 order.getId(),
                 order.getStatus());
        log.debug("SMS message: {}", smsMessage);
    }

    private String buildSMSMessage(Order order) {
        return String.format(
                "Order #%d update: %s. Total: $%.2f. Track at: example.com/orders/%d",
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getId()
        );
    }
}

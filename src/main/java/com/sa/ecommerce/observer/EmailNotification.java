package com.sa.ecommerce.observer;

import com.sa.ecommerce.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Concrete Observer for Email notifications
 * Observer Pattern implementation
 */
@Slf4j
@Component
public class EmailNotification implements OrderObserver {

    @Override
    public void update(Order order) {
        log.debug("EmailNotification: Sending email notification for order ID: {}", order.getId());

        // TODO: Integrate with actual email service (e.g., SendGrid, AWS SES)
        String emailBody = buildEmailBody(order);

        log.info("EmailNotification: Email sent to {} - Order Status: {}",
                 order.getCustomerEmail(),
                 order.getStatus());
        log.debug("Email body: {}", emailBody);
    }

    private String buildEmailBody(Order order) {
        return String.format("""
                Dear %s,

                Your order (ID: %d) status has been updated to: %s

                Order Details:
                - Product: %s
                - Quantity: %d
                - Total Amount: $%.2f

                Thank you for your order!

                Best regards,
                E-Commerce Team
                """,
                order.getCustomerName(),
                order.getId(),
                order.getStatus(),
                order.getProductName(),
                order.getQuantity(),
                order.getTotalAmount()
        );
    }
}

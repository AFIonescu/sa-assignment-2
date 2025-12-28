package com.sa.ecommerce.observer;

import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private NotificationService notificationService;
    private OrderObserver mockObserver1;
    private OrderObserver mockObserver2;
    private Order order;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService();
        mockObserver1 = mock(OrderObserver.class);
        mockObserver2 = mock(OrderObserver.class);

        order = Order.builder()
                .id(1L)
                .customerName("John Doe")
                .customerEmail("john@example.com")
                .productName("Laptop")
                .quantity(1)
                .totalAmount(1000.0)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .status(OrderStatus.CONFIRMED)
                .build();
    }

    @Test
    void testAddObserver() {
        notificationService.addObserver(mockObserver1);
        notificationService.notifyObservers(order);

        verify(mockObserver1, times(1)).update(order);
    }

    @Test
    void testRemoveObserver() {
        notificationService.addObserver(mockObserver1);
        notificationService.removeObserver(mockObserver1);
        notificationService.notifyObservers(order);

        verify(mockObserver1, never()).update(order);
    }

    @Test
    void testNotifyMultipleObservers() {
        notificationService.addObserver(mockObserver1);
        notificationService.addObserver(mockObserver2);
        notificationService.notifyObservers(order);

        verify(mockObserver1, times(1)).update(order);
        verify(mockObserver2, times(1)).update(order);
    }

    @Test
    void testEmailNotification() {
        EmailNotification emailNotification = new EmailNotification();
        assertDoesNotThrow(() -> emailNotification.update(order));
    }

    @Test
    void testSMSNotification() {
        SMSNotification smsNotification = new SMSNotification();
        assertDoesNotThrow(() -> smsNotification.update(order));
    }
}

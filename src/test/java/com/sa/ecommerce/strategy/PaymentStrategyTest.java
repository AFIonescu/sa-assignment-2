package com.sa.ecommerce.strategy;

import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStrategyTest {

    private CreditCardPayment creditCardPayment;
    private PayPalPayment payPalPayment;
    private CryptocurrencyPayment cryptocurrencyPayment;
    private Order order;

    @BeforeEach
    void setUp() {
        creditCardPayment = new CreditCardPayment();
        payPalPayment = new PayPalPayment();
        cryptocurrencyPayment = new CryptocurrencyPayment();

        order = Order.builder()
                .id(1L)
                .customerName("John Doe")
                .customerEmail("john@example.com")
                .productName("Laptop")
                .quantity(1)
                .totalAmount(1000.0)
                .status(OrderStatus.PENDING)
                .build();
    }

    @Test
    void testCreditCardPaymentSuccess() {
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setPaymentDetails("1234567890123456");

        boolean result = creditCardPayment.processPayment(order);
        assertTrue(result);
    }

    @Test
    void testCreditCardPaymentFailsWithInvalidCard() {
        order.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        order.setPaymentDetails("123");

        boolean result = creditCardPayment.processPayment(order);
        assertFalse(result);
    }

    @Test
    void testPayPalPaymentSuccess() {
        order.setPaymentMethod(PaymentMethod.PAYPAL);
        order.setPaymentDetails("john@paypal.com");

        boolean result = payPalPayment.processPayment(order);
        assertTrue(result);
    }

    @Test
    void testPayPalPaymentFailsWithInvalidEmail() {
        order.setPaymentMethod(PaymentMethod.PAYPAL);
        order.setPaymentDetails("invalid-email");

        boolean result = payPalPayment.processPayment(order);
        assertFalse(result);
    }

    @Test
    void testCryptocurrencyPaymentSuccess() {
        order.setPaymentMethod(PaymentMethod.CRYPTOCURRENCY);
        order.setPaymentDetails("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");

        boolean result = cryptocurrencyPayment.processPayment(order);
        assertTrue(result);
    }

    @Test
    void testCryptocurrencyPaymentFailsWithInvalidWallet() {
        order.setPaymentMethod(PaymentMethod.CRYPTOCURRENCY);
        order.setPaymentDetails("short");

        boolean result = cryptocurrencyPayment.processPayment(order);
        assertFalse(result);
    }

    @Test
    void testPaymentStrategyNames() {
        assertEquals("Credit Card Payment", creditCardPayment.getStrategyName());
        assertEquals("PayPal Payment", payPalPayment.getStrategyName());
        assertEquals("Cryptocurrency Payment", cryptocurrencyPayment.getStrategyName());
    }
}

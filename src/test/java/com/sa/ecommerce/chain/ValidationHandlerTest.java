package com.sa.ecommerce.chain;

import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import com.sa.ecommerce.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationHandlerTest {

    private CustomerValidationHandler customerValidationHandler;
    private InventoryCheckHandler inventoryCheckHandler;
    private PaymentValidationHandler paymentValidationHandler;
    private Order validOrder;

    @BeforeEach
    void setUp() {
        customerValidationHandler = new CustomerValidationHandler();
        inventoryCheckHandler = new InventoryCheckHandler();
        paymentValidationHandler = new PaymentValidationHandler();

        // Set up chain
        customerValidationHandler.setNext(inventoryCheckHandler);
        inventoryCheckHandler.setNext(paymentValidationHandler);

        // Create valid order
        validOrder = Order.builder()
                .customerName("John Doe")
                .customerEmail("john@example.com")
                .productName("Laptop")
                .quantity(2)
                .totalAmount(2000.0)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentDetails("1234567890123456")
                .status(OrderStatus.PENDING)
                .build();
    }

    @Test
    void testValidOrderPassesAllValidations() {
        boolean result = customerValidationHandler.handle(validOrder);
        assertTrue(result);
    }

    @Test
    void testInvalidCustomerNameFailsValidation() {
        validOrder.setCustomerName("");
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }

    @Test
    void testInvalidEmailFailsValidation() {
        validOrder.setCustomerEmail("invalid-email");
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }

    @Test
    void testInvalidQuantityFailsValidation() {
        validOrder.setQuantity(0);
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }

    @Test
    void testQuantityExceedsInventoryFailsValidation() {
        validOrder.setQuantity(150);
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }

    @Test
    void testMissingPaymentMethodFailsValidation() {
        validOrder.setPaymentMethod(null);
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }

    @Test
    void testMissingPaymentDetailsFailsValidation() {
        validOrder.setPaymentDetails("");
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }

    @Test
    void testInvalidTotalAmountFailsValidation() {
        validOrder.setTotalAmount(0.0);
        boolean result = customerValidationHandler.handle(validOrder);
        assertFalse(result);
    }
}

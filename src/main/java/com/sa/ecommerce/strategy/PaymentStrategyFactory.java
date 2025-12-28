package com.sa.ecommerce.strategy;

import com.sa.ecommerce.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory to get the appropriate payment strategy based on payment method
 */
@Component
@RequiredArgsConstructor
public class PaymentStrategyFactory {

    private final CreditCardPayment creditCardPayment;
    private final PayPalPayment payPalPayment;
    private final CryptocurrencyPayment cryptocurrencyPayment;

    public PaymentStrategy getStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CREDIT_CARD -> creditCardPayment;
            case PAYPAL -> payPalPayment;
            case CRYPTOCURRENCY -> cryptocurrencyPayment;
        };
    }
}

package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.service.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.SetupIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService implements IPaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    public PaymentIntent createPaymentIntent(Double amount, String currency, String paymentMethodId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        if (paymentMethodId != null) {
            params.put("payment_method", paymentMethodId);
        }
        return PaymentIntent.create(params);
    }

    public PaymentIntent confirmPaymentIntent(String clientSecret, String paymentMethodId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(clientSecret);
        if (paymentIntent.getStatus().equals("requires_action")) {
            paymentIntent.confirm(Map.of("payment_method", paymentMethodId));
        }
        return paymentIntent;
    }

    public SetupIntent createSetupIntent(String email, List<String> paymentMethodTypes) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        Map<String, Object> params = new HashMap<>();
        params.put("customer", createCustomer(email).getId());
        params.put("payment_method_types", paymentMethodTypes);
        return SetupIntent.create(params);
    }

    private Customer createCustomer(String email) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return Customer.create(params);
    }

    public PaymentIntent chargeSavedCard(String email) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        Customer customer = Customer.list(Map.of("email", email)).getData().get(0);
        PaymentMethod paymentMethod = PaymentMethod.list(Map.of("customer", customer.getId(), "type", "card")).getData().get(0);
        Map<String, Object> params = new HashMap<>();
        params.put("amount", calculateOrderAmount());  // Implement calculateOrderAmount()
        params.put("currency", "usd");
        params.put("customer", customer.getId());
        params.put("payment_method", paymentMethod.getId());
        params.put("confirm", true);
        params.put("off_session", true);
        return PaymentIntent.create(params);
    }
}

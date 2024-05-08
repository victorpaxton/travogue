package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Payment.PaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;

import java.util.List;

public interface IPaymentService {

    PaymentIntent createPaymentIntent(Double amount, String currency, String paymentMethodId) throws StripeException;
    PaymentIntent confirmPaymentIntent(String clientSecret, String paymentMethodId) throws StripeException;
    SetupIntent createSetupIntent(String email, List<String> paymentMethodTypes) throws StripeException;
    Customer createCustomer(String email) throws StripeException;
    PaymentIntent chargeSavedCard(String email) throws StripeException;
}

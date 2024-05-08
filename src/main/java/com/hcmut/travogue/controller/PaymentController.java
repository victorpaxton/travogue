package com.hcmut.travogue.controller;

import com.hcmut.travogue.model.dto.Payment.ConfirmationRequest;
import com.hcmut.travogue.model.dto.Payment.PaymentRequest;
import com.hcmut.travogue.model.dto.Payment.SetupIntentRequest;
import com.hcmut.travogue.service.IPaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.SetupIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/create-intent")
    public ResponseEntity<PaymentIntent> createPaymentIntent(@RequestBody PaymentRequest request) throws StripeException {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(request.getAmount(), request.getCurrency(), request.getPaymentMethodId());
        return ResponseEntity.ok(paymentIntent);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody ConfirmationRequest request) throws StripeException {
        paymentService.confirmPaymentIntent(request.getClientSecret(), request.getPaymentMethodId());
        return ResponseEntity.ok("Payment successful!");
    }

    @PostMapping("/create-setup-intent")
    public ResponseEntity<SetupIntent> createSetupIntent(@RequestBody SetupIntentRequest request) throws StripeException {
        return ResponseEntity.ok(paymentService.createSetupIntent(request.getEmail(), request.getPaymentMethodTypes()));
    }

//    @PostMapping("/charge-card")
//    public ResponseEntity<PaymentIntent> chargeSavedCard(@RequestBody ChargeRequest request) throws StripeException {
//        return ResponseEntity.ok(paymentService.chargeSavedCard(request.getEmail()));
//    }



}

package com.hcmut.travogue.model.dto.Payment;

import lombok.Data;

@Data
public class PaymentRequest {
    private Double amount;
    private String currency;
    private String paymentMethodId;
}

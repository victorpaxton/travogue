package com.hcmut.travogue.model.dto.Payment;

import lombok.Data;

@Data
public class ConfirmationRequest {
    private String clientSecret;
    private String paymentMethodId;
}

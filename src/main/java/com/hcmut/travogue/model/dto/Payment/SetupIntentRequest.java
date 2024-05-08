package com.hcmut.travogue.model.dto.Payment;

import lombok.Data;

import java.util.List;

@Data
public class SetupIntentRequest {
    private String email;
    private List<String> paymentMethodTypes;
}

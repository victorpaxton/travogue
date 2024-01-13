package com.hcmut.travogue.model.dto.Ticket;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentInfoCreateDTO {
    private String provider;

    private String accountNumber;

    @Temporal(TemporalType.DATE)
    private Date expiredDate;

    private boolean isDefault;
}

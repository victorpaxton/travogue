package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Ticket.PaymentInfoCreateDTO;
import com.hcmut.travogue.model.entity.Ticket.PaymentInfo;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface IPaymentInfoService {

    public PaymentInfo addNewPaymentInfo(Principal principal, UUID paymentTypeId, PaymentInfoCreateDTO paymentInfoCreateDTO);

    List<PaymentInfo> getPaymentInfosByType(Principal principal, UUID paymentTypeId);
}

package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Ticket.PaymentTypeCreateDTO;
import com.hcmut.travogue.model.entity.Ticket.PaymentType;

import java.util.List;

public interface IPaymentTypeService {

    public List<PaymentType> getAll();

    public PaymentType addPaymentType(PaymentTypeCreateDTO paymentTypeCreateDTO);
}

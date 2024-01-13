package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Ticket.PaymentTypeCreateDTO;
import com.hcmut.travogue.model.entity.Ticket.PaymentType;
import com.hcmut.travogue.repository.Ticket.PaymentTypeRepository;
import com.hcmut.travogue.service.IPaymentTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService implements IPaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PaymentType> getAll() {
        return paymentTypeRepository.findAll();
    }

    @Override
    public PaymentType addPaymentType(PaymentTypeCreateDTO paymentTypeCreateDTO) {
        PaymentType newPaymentType = modelMapper.map(paymentTypeCreateDTO, PaymentType.class);

        return paymentTypeRepository.save(newPaymentType);
    }
}

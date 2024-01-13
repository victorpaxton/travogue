package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Ticket.PaymentInfoCreateDTO;
import com.hcmut.travogue.model.entity.Ticket.PaymentInfo;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Ticket.PaymentInfoRepository;
import com.hcmut.travogue.repository.Ticket.PaymentTypeRepository;
import com.hcmut.travogue.service.IPaymentInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentInfoService implements IPaymentInfoService {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaymentInfo addNewPaymentInfo(Principal principal, UUID paymentTypeId, PaymentInfoCreateDTO paymentInfoCreateDTO) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        PaymentInfo newPaymentInfo = modelMapper.map(paymentInfoCreateDTO, PaymentInfo.class);

        newPaymentInfo.setPaymentType(paymentTypeRepository.findById(paymentTypeId).orElseThrow());
        newPaymentInfo.setUser(user);

        return paymentInfoRepository.save(newPaymentInfo);
    }

    @Override
    public List<PaymentInfo> getPaymentInfosByType(Principal principal, UUID paymentTypeId) {
        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();

        return paymentInfoRepository.findByPaymentTypeIdAndUserId(paymentTypeId, user.getId());
    }
}

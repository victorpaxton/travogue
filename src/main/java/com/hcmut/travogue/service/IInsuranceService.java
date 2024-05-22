package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Ticket.InsuranceCreateDTO;
import com.hcmut.travogue.model.entity.Ticket.InsuranceCompany;

import java.util.List;
import java.util.UUID;

public interface IInsuranceService {
    InsuranceCompany createInsurance(InsuranceCreateDTO insuranceCreateDTO);

    List<InsuranceCompany> getInsurances();

    InsuranceCompany getInsuranceById(UUID id);
}

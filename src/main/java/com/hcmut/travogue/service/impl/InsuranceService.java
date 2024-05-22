package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Ticket.InsuranceCreateDTO;
import com.hcmut.travogue.model.entity.Ticket.InsuranceCompany;
import com.hcmut.travogue.repository.Ticket.InsuranceCompanyRepository;
import com.hcmut.travogue.service.IInsuranceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InsuranceService implements IInsuranceService {

    @Autowired
    private InsuranceCompanyRepository insuranceCompanyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InsuranceCompany createInsurance(InsuranceCreateDTO insuranceCreateDTO) {
        return insuranceCompanyRepository.save(
                modelMapper.map(insuranceCreateDTO, InsuranceCompany.class)
        );
    }

    @Override
    public List<InsuranceCompany> getInsurances() {
        return insuranceCompanyRepository.findAll();
    }

    @Override
    public InsuranceCompany getInsuranceById(UUID id) {
        return insuranceCompanyRepository.findById(id).orElseThrow();
    }

}

package com.hcmut.travogue.controller.Ticket;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.Ticket.InsuranceCreateDTO;
import com.hcmut.travogue.service.IInsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/insurances")
public class InsuranceCompanyController {

    @Autowired
    private IInsuranceService insuranceService;

    @PostMapping
    @Operation(summary = "Add an insurance package")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> createInsurance(@RequestBody InsuranceCreateDTO insuranceCreateDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(insuranceService.createInsurance(insuranceCreateDTO))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get insurances")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getInsurances() {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(insuranceService.getInsurances())
                .errors(null)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a insurance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getInsuranceById(@PathVariable("id") UUID id) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(insuranceService.getInsuranceById(id))
                .errors(null)
                .build();
    }



}

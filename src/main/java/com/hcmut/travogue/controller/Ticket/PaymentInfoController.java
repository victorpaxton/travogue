package com.hcmut.travogue.controller.Ticket;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.Ticket.PaymentInfoCreateDTO;
import com.hcmut.travogue.service.IPaymentInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/payment-infos")
public class PaymentInfoController {

    @Autowired
    private IPaymentInfoService paymentInfoService;

    @PostMapping
    @Operation(summary = "A user add a new payment info")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addNewPaymentInfo(Principal principal,
                                                   @RequestParam UUID paymentTypeId,
                                                   @RequestBody PaymentInfoCreateDTO paymentInfoCreateDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(paymentInfoService.addNewPaymentInfo(principal, paymentTypeId, paymentInfoCreateDTO))
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get payment info by type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getPaymentInfosByType(Principal principal,
                                                   @RequestParam UUID paymentTypeId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(paymentInfoService.getPaymentInfosByType(principal, paymentTypeId))
                .errors(null)
                .build();
    }

}

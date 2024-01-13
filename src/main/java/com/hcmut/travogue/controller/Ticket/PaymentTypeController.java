package com.hcmut.travogue.controller.Ticket;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.Ticket.PaymentTypeCreateDTO;
import com.hcmut.travogue.service.IPaymentTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-types")
public class PaymentTypeController {

    @Autowired
    private IPaymentTypeService paymentTypeService;

    @GetMapping
    @Operation(summary = "Get all acceptable payment types")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getAll() {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(paymentTypeService.getAll())
                .errors(null)
                .build();
    }

    @PostMapping
    @Operation(summary = "Add a new payment type")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addPaymentType(@RequestBody PaymentTypeCreateDTO paymentTypeCreateDTO) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(paymentTypeService.addPaymentType(paymentTypeCreateDTO))
                .errors(null)
                .build();
    }
}

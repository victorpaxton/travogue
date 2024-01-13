package com.hcmut.travogue.controller.TravelActivity;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.TravelActivity.PromotionCreateDTO;
import com.hcmut.travogue.service.IPromotionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private IPromotionService promotionService;

    @PostMapping
    @Operation(summary = "Add a promotion")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<Object> addPromotion(@RequestParam UUID activityId, @RequestBody PromotionCreateDTO promotionCreateDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(promotionService.addPromotion(activityId, promotionCreateDTO))
                .errors(null)
                .build();

    }

    @GetMapping
    @Operation(summary = "Get promotion of a travel activity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getPromotionsByActivity(@RequestParam UUID activityId) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(promotionService.getPromotionsByActivity(activityId))
                .errors(null)
                .build();
    }

    @PostMapping("/validation")
    @Operation(summary = "Check a discount code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> checkValidDiscountCode(@RequestParam UUID activityId,
                                                        @RequestParam String discountCode) {
        promotionService.checkValidDiscountCode(activityId, discountCode);

        return ResponseModel.builder()
                .isSuccess(true)
                .data("Valid discount code")
                .errors(null)
                .build();

    }



}

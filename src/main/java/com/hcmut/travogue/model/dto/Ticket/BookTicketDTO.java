package com.hcmut.travogue.model.dto.Ticket;

import lombok.Data;

import java.util.UUID;

@Data
public class BookTicketDTO {
    private String notes;
    private int adultsPrice;
    private int childrenPrice;
    private int babyPrice;
    private int numOfAdults;
    private int numOfChildren;
    private int numOfBabies;
    private int insuranceCost;
    private int totalDiscountCode;
    private int totalDiscountEvent;
    private int totalPay;
    private UUID insuranceId;
}

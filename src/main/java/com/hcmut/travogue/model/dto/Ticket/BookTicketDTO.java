package com.hcmut.travogue.model.dto.Ticket;

import lombok.Data;

@Data
public class BookTicketDTO {
    private String notes;
    private int adultsPrice;
    private int childrenPrice;
    private int babyPrice;
    private int numOfAdults;
    private int numOfChildren;
    private int numOfBabies;
    private int totalDiscountCode;
    private int totalDiscountEvent;
    private int totalPay;
}

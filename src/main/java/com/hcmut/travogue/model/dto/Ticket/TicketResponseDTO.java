package com.hcmut.travogue.model.dto.Ticket;

import com.hcmut.travogue.model.entity.Ticket.Ticket;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponseDTO {
    private TravelActivity activity;
    private Ticket ticket;
}

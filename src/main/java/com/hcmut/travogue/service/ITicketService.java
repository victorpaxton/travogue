package com.hcmut.travogue.service;

import com.hcmut.travogue.model.dto.Ticket.BookTicketDTO;
import com.hcmut.travogue.model.entity.Ticket.Ticket;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.UUID;

public interface ITicketService {

    public Ticket bookTicket(Principal principal, UUID paymentInfoId, UUID activityTimeFrameId, BookTicketDTO bookTicketDTO);

    public void cancelTicket(UUID ticketId);

    public Page<Ticket> getTicketsByActivityTimeFrame(UUID activityTimeFrameId, int pageNumber, int pageSize, String sortField);

    Ticket getTicket(UUID ticketId);
}

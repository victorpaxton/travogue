package com.hcmut.travogue.service.impl;

import com.hcmut.travogue.model.dto.Ticket.BookTicketDTO;
import com.hcmut.travogue.model.entity.Ticket.PaymentInfo;
import com.hcmut.travogue.model.entity.Ticket.Ticket;
import com.hcmut.travogue.model.entity.Ticket.TicketStatus;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityTimeFrame;
import com.hcmut.travogue.model.entity.TravelActivity.TravelActivity;
import com.hcmut.travogue.model.entity.User.Host;
import com.hcmut.travogue.model.entity.User.SessionUser;
import com.hcmut.travogue.model.entity.User.User;
import com.hcmut.travogue.repository.Ticket.PaymentInfoRepository;
import com.hcmut.travogue.repository.Ticket.TicketRepository;
import com.hcmut.travogue.repository.TravelActivity.ActivityTimeFrameRepository;
import com.hcmut.travogue.service.ITicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
public class TicketService implements ITicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private ActivityTimeFrameRepository activityTimeFrameRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Ticket bookTicket(Principal principal, UUID paymentInfoId, UUID activityTimeFrameId, BookTicketDTO bookTicketDTO) {
        Ticket newTicket = modelMapper.map(bookTicketDTO, Ticket.class);

        if (paymentInfoId == null) {
            newTicket.setTicketStatus(TicketStatus.PAY_AT_PICK_UP);
        } else {
            newTicket.setPaymentInfo(paymentInfoRepository.findById(paymentInfoId).orElseThrow());
            newTicket.setTicketStatus(TicketStatus.PAID);
        }

        User user = ((SessionUser) ((Authentication) principal).getPrincipal()).getUserInfo();
        newTicket.setUser(user);

        ActivityTimeFrame activityTimeFrame = activityTimeFrameRepository.findById(activityTimeFrameId).orElseThrow();
        newTicket.setActivityTimeFrame(activityTimeFrame);
        activityTimeFrame.setNumOfRegisteredGuests(activityTimeFrame.getNumOfRegisteredGuests() + 1);
        activityTimeFrameRepository.save(activityTimeFrame);

        return ticketRepository.save(newTicket);
    }

    @Override
    public void cancelTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();

        ticket.setTicketStatus(TicketStatus.CANCEL);
        ticketRepository.save(ticket);
    }

    @Override
    public Page<Ticket> getTicketsByActivityTimeFrame(UUID activityTimeFrameId, int pageNumber, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());

        return ticketRepository.findByActivityTimeFrame_Id(activityTimeFrameId, pageable);
    }

    @Override
    public Ticket getTicket(UUID ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow();
    }
}

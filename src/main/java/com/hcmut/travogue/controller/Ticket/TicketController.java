package com.hcmut.travogue.controller.Ticket;

import com.hcmut.travogue.model.dto.Response.ResponseModel;
import com.hcmut.travogue.model.dto.Ticket.BookTicketDTO;
import com.hcmut.travogue.repository.Ticket.TicketRepository;
import com.hcmut.travogue.service.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private ITicketService ticketService;

    @PostMapping
    @Operation(summary = "Buy a ticket")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> bookTicket(Principal principal,
                                            @RequestParam(required = false) UUID paymentInfoId,
                                            @RequestParam UUID activityTimeFrameId,
                                            @RequestBody BookTicketDTO bookTicketDTO) {
        return ResponseModel.builder()
                .isSuccess(true)
                .data(ticketService.bookTicket(principal, paymentInfoId, activityTimeFrameId, bookTicketDTO))
                .errors(null)
                .build();

    }

    @PutMapping("/{id}")
    @Operation(summary = "Cancel a ticket")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> cancelTicket(@PathVariable("id") UUID ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseModel.builder()
                .isSuccess(true)
                .data("Cancel successfully")
                .errors(null)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all tickets of a activity time frame")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getTicketsByActivityTimeFrame(@RequestParam UUID activityTimeFrameId,
                                                               @RequestParam(defaultValue = "0") int pageNumber,
                                                               @RequestParam(defaultValue = "4") int pageSize,
                                                               @RequestParam(defaultValue = "created_at") String sortField) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(ticketService.getTicketsByActivityTimeFrame(activityTimeFrameId, pageNumber, pageSize, sortField))
                .errors(null)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a detailed ticket")
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> getTicket(@PathVariable("id") UUID ticketId) {

        return ResponseModel.builder()
                .isSuccess(true)
                .data(ticketService.getTicket(ticketId))
                .errors(null)
                .build();
    }

}

package com.hcmut.travogue.repository.Ticket;

import com.hcmut.travogue.model.entity.Ticket.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Page<Ticket> findByActivityTimeFrame_Id(UUID activityTimeFrameId, Pageable pageable);
}

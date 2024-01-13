package com.hcmut.travogue.repository.Ticket;

import com.hcmut.travogue.model.entity.Ticket.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, UUID> {
}

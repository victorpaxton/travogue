package com.hcmut.travogue.model.entity.Ticket;

import com.hcmut.travogue.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Table(name = "insurance_company")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class InsuranceCompany extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private int bestOffer;

    private String benefits;

    @Column(columnDefinition = "text")
    private String url;
}

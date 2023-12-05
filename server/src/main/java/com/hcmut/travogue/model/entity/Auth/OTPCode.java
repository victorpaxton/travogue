package com.hcmut.travogue.model.entity.Auth;

import com.hcmut.travogue.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "otp_code")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OTPCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "is_verified", columnDefinition = "boolean default false")
    private boolean isVerified;
}

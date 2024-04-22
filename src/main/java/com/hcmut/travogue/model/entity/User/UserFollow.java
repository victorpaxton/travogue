package com.hcmut.travogue.model.entity.User;

import com.hcmut.travogue.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_follow")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserFollow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_follow_id", referencedColumnName = "id")
    private User from;

    @ManyToOne
    @JoinColumn(name = "user_followed_id", referencedColumnName = "id")
    private User to;
}

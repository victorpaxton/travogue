package com.hcmut.travogue.model.entity.Post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Table(name = "post_comment")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class PostComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonIgnore
    private Post post;
}

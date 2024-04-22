package com.hcmut.travogue.model.entity.Post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "text")
    private String caption;

    @Column(columnDefinition = "text")
    private String images;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<PostComment> postComments;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<PostLike> postLikes;
}

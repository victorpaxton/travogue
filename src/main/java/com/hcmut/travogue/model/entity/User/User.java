package com.hcmut.travogue.model.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmut.travogue.model.entity.BaseEntity;
import com.hcmut.travogue.model.entity.Plan.Plan;
import com.hcmut.travogue.model.entity.Post.Post;
import com.hcmut.travogue.model.entity.Post.PostComment;
import com.hcmut.travogue.model.entity.Post.PostLike;
import com.hcmut.travogue.model.entity.Post.PostUserTagged;
import com.hcmut.travogue.model.entity.Ticket.Ticket;
import com.hcmut.travogue.model.entity.TravelActivity.ActivityComment;
import com.hcmut.travogue.model.entity.TravelActivity.Wishlist;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    protected String email;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @JsonIgnore
    protected String password;

    @Temporal(TemporalType.DATE)
    protected Date birthdate;

    protected String phone;

    protected String avatar;

    @Column(name = "bio_intro")
    protected String bioIntro;

    @JsonIgnore
    protected String roles;

    @Column(name = "is_enabled")
    protected boolean isEnabled;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<ActivityComment> activityComments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<PostComment> postComments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<PostLike> postLikes;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<Ticket> tickets;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<Plan> plans;

    @OneToMany(mappedBy = "to")
    @JsonIgnore
    protected List<UserFollow> followers;

    @OneToMany(mappedBy = "from")
    @JsonIgnore
    protected List<UserFollow> following;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<PostUserTagged> postUserTaggedList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    protected List<Wishlist> wishlists;
}

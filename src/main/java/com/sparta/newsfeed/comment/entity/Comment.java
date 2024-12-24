package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.scheduling.config.Task;

import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "newsfeed_id")
    private Newsfeed newsfeed;

//    @ManyToMany
//    @JoinTable(
//            name = "comment_likes",
//            joinColumns = @JoinColumn(name = "comment_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    @JoinColumn(name = "like_user_ids")
//    private List<User> likeUserIdList;

    public Comment(String contents) {
        this.contents = contents;
    }

    public void setUserAndNewsfeed(User user, Newsfeed newsfeed) {
        this.user = user;
        this.newsfeed = newsfeed;
    }
}

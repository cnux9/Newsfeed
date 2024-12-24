package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.scheduling.config.Task;


@Getter
@Entity
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "newsfeed_id")
    private Newsfeed newsfeed;

    public Comment(String contents) {
        this.contents = contents;
    }

    public Comment() {

    }

    public void setUserAndNewsfeed(User user, Newsfeed newsfeed) {
        this.user = user;
        this.newsfeed = newsfeed;
    }
}

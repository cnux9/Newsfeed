package com.sparta.newsfeed.comment.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.comment.dto.CommentRequestDto;
import com.sparta.newsfeed.newsfeed.entity.Newsfeed;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@NoArgsConstructor
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
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "newsfeed_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Newsfeed newsfeed;

    public Comment(String contents, User user, Newsfeed newsfeed) {
        this.contents = contents;
        this.user = user;
        this.newsfeed = newsfeed;
    }

    public Comment partialUpdate(CommentRequestDto dto){
        this.contents = dto.contents();
        return this;
    }
}

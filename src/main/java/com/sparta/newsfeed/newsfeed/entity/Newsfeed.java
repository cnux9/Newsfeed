package com.sparta.newsfeed.newsfeed.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "newsfeed")
@EntityListeners(AuditingEntityListener.class)
public class Newsfeed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String contents;

    public Newsfeed(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}

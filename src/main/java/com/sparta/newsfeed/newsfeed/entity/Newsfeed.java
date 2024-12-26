package com.sparta.newsfeed.newsfeed.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newsfeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "newsfeed")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
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

    @OneToMany(mappedBy = "newsfeed")
    private List<User> likedUsers = new ArrayList<>();

    public Newsfeed(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public Newsfeed partialUpdate(NewsfeedRequestDto dto){
        this.title = dto.getTitle();
        this.contents = dto.getContent();
        return this;
    }

    public void addLiked(User user){
        User targetUser = this.likedUsers.stream()
                .filter(likedUser -> likedUser.getId().equals(user.getId()))
                .findAny()
                .orElse(null);
        if(targetUser == null)
            this.likedUsers.add(user);
    }

    public void removeLiked(User user){
        this.likedUsers.stream()
                .filter(likedUser -> likedUser.getId().equals(user.getId()))
                .findAny()
                .ifPresent(targetUser -> this.likedUsers.remove(user));
    }
}

package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Setter
    @Column
    private String name;

    @Setter
    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}

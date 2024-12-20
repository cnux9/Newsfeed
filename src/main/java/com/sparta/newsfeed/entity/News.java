package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.scheduling.config.Task;

import java.util.List;

@Getter
@Entity
@Table(name = "newsfeed")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class News extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Setter
    @Column
    private String title;

    private String contents;
}

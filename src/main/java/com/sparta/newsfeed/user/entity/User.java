package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
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
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    /*
    todo : 메서드 이름에 대한 변경의 건, 확인 후 변경이 필요.
     */
    public void toUserResponseDto(UserRequestDto requestDto){
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }
}

package com.sparta.newsfeed.user.entity;

import com.sparta.newsfeed.BaseEntity;
import com.sparta.newsfeed.user.dto.SignUpRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
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

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {

    }
    // TODO: 삭제된 사용자의 이메일을 보관하는 테이블 분리?
    public void updateSoftDelete() {
        this.isDeleted = true;
    }

    /*
    todo : 메서드 이름에 대한 변경의 건, 확인 후 변경이 필요.
     */
    public void partialUpdate(String name, String password){
        this.name = name;
        this.password = password;
    }
}

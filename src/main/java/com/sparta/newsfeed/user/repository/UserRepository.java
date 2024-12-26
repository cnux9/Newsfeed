package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /*
    주석처리된 부분은 유저 이름으로 호출할 필요가 있으시면 쓰세욥
     */
    //Optional<User> findUserByName(String username);
    Optional<User> findUserByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsUserByEmail(String email);
}

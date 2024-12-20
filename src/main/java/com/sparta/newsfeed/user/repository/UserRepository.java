package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    /*
    주석처리된 부분은 유저 이름으로 호출할 필요가 있으시면 쓰세욥
     */
    //Optional<User> findUserByName(String username);
    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email){
        return findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist email" + email));
    }

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

//    default User findUserByNameOrElseThrow(String name) {
//        return findUserByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + username));
//    }
}

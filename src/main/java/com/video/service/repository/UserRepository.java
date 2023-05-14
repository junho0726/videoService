package com.video.service.repository;

import com.video.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByid(String id);
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.pw = :pw")
    User userLogin(String id, String pw);
}

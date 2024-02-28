package com.example.gymAp.dao;

import com.example.gymAp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserDAO  extends JpaRepository<User, Long> {


    @Transactional(readOnly = true)
    Optional<User> findUserByUserName(String username);

    boolean existsUserByUserName(String username);
}
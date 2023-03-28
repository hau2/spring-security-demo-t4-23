package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE user SET is_enable = :isEnable WHERE username = :username",
            nativeQuery = true
    )
    void updateIsEnableByUsername(
            @Param("isEnable") Boolean isEnable,
            @Param("username") String username);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE user SET count_fail_login = :countFailLogin WHERE username = :username",
            nativeQuery = true
    )
    void updateCountFailLoginByUsername(
            @Param("countFailLogin") int countFailLogin,
            @Param("username") String username);

    @Transactional
    @Query(
            value = "SELECT count_fail_login FROM user  WHERE username = :username",
            nativeQuery = true
    )
    Integer getCountFailLoginByUsername(@Param("username") String username);

}

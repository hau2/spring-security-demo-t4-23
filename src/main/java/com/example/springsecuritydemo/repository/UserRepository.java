package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.query.PrivateCodeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query(value = "select * from user where private_code = ?;", nativeQuery = true)
    Optional<User> finByPrivateCode(String privateCode);
    @Query(value = "select private_code, expiry_date_code from user where mail = ?;", nativeQuery = true)
    PrivateCodeResult findPrivateCodeByMail(String mail);
    @Modifying
    @Transactional
    @Query(value = "update user set private_code = ?, expiry_date_code = ? where mail = ?;", nativeQuery = true)
    void setPrivateCodeForUser(String code, LocalDateTime expiryDateCode, String mail);
    @Modifying
    @Query(value = "update user set password = ? where mail = ?", nativeQuery = true)
    void setNewPassword(String mail, String newPassword);
}

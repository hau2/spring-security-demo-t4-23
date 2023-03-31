package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.query.PrivateCodeResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE user SET is_enable = :isEnable WHERE mail = :mail",
            nativeQuery = true
    )
    void updateIsEnableByMail(
            @Param("isEnable") Boolean isEnable,
            @Param("mail") String mail);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE user SET count_fail_login = :countFailLogin WHERE mail = :mail",
            nativeQuery = true
    )
    void updateCountFailLoginByMail(
            @Param("countFailLogin") int countFailLogin,
            @Param("mail") String mail);

    @Transactional
    @Query(
            value = "SELECT count_fail_login FROM user  WHERE mail = :mail",
            nativeQuery = true
    )
    Integer getCountFailLoginByMail(@Param("mail") String mail);

    @Query(value = "select * from user where private_code = ?;", nativeQuery = true)
    Optional<User> finByPrivateCode(String privateCode);

    @Query(value = "select private_code, expiry_date_code from user where mail = ?;", nativeQuery = true)
    PrivateCodeResult findPrivateCodeByMail(String mail);

    @Query(value = "select expiry_date_code from user where private_code = ?", nativeQuery = true)
    LocalDateTime findExpiryDateOfPrivateCode(String privateCode);

    @Modifying
    @Transactional
    @Query(value = "update user set private_code = ?, expiry_date_code = ? where mail = ?;", nativeQuery = true)
    void setPrivateCodeForUser(String code, LocalDateTime expiryDateCode, String mail);

    @Modifying
    @Transactional
    @Query(value = "update user set password = ? where private_code = ?", nativeQuery = true)
    void setNewPassword(String newPassword, String privateCode);

    @Modifying
    @Transactional
    @Query(value = "update user set is_enable = true where private_code = ?", nativeQuery = true)
    void unlockUser(String privateCode);

    @Modifying
    @Transactional
    @Query(value = "update user set private_code = null, expiry_date_code = null where private_code = ?", nativeQuery = true)
    void killPrivateCode(String privateCode);
}

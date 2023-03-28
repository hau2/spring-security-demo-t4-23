package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query(value = "select role.role_name from user_role inner join role where user_role.role_id = role.role_id", nativeQuery = true)
    List<String> findAllRoleByUserId(Integer userId);
}

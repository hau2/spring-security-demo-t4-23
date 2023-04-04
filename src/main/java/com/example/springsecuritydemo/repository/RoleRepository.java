package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    @Query(
//            value = "SELECT * FROM role WHERE role_name=?",
//            nativeQuery = true

//    )
    Optional<Role> findByRoleName(String roleName);

}

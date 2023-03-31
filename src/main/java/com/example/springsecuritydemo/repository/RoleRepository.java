package com.example.springsecuritydemo.repository;

import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

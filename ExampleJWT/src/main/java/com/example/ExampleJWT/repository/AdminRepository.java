package com.example.ExampleJWT.repository;

import com.example.ExampleJWT.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Integer>{
    Admin findByUsernameIgnoreCase(String username);
}

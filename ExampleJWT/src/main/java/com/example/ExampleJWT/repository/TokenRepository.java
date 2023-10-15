package com.example.ExampleJWT.repository;

import com.example.ExampleJWT.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query(" SELECT t FROM  Token t INNER JOIN  Admin a on t.admin.id = a.id WHERE " +
            "a.id = :adminId AND (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByAdmin(Integer adminId);

    @Query(" SELECT a FROM Token a WHERE (?1 IS Null OR a.admin.id =?1)")
    List<Token> findByAdmin(Integer adminId);

    Optional<Token> findByToken(String token);

}

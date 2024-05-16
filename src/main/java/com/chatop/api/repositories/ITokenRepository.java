package com.chatop.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.Token;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "SELECT tokens.* FROM TOKENS tokens INNER JOIN USERS user ON tokens.user_id = user.id WHERE user.id = :id AND tokens.expired = false", nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer id);

    Token findByToken(String token);

}
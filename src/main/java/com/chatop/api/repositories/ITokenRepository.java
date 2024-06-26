package com.chatop.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.Token;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {

    /**
     * This method retrieves all valid tokens for a specific user.
     *
     * @param id the id of the user to fetch tokens for
     * @return a list of tokens that belong to the specified user and are not
     * expired
     */
    @Query(value = "SELECT tokens.* FROM TOKENS tokens INNER JOIN USERS user ON tokens.user_id = user.id WHERE user.id = :id AND tokens.expired = false", nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer id);

    Token findByToken(String token);

}

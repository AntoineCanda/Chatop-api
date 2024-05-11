package com.chatop.api.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "TOKENS")
public class Token implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name="token_type")
    private TokenType type = TokenType.BEARER_TOKEN;

    @Column(name="expired")
    private boolean expired;

    @Column(name="user_id")
    private Integer user_id;

}

package com.chatop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
}

package com.chatop.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.Message;

@Repository
public interface IMessageRepository extends CrudRepository<Message, Integer> {

}

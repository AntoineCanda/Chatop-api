package com.chatop.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.Rental;

@Repository
public interface IRentalRepository extends CrudRepository<Rental, Integer> {

}

package com.shaowei.shop.repository;

import com.shaowei.shop.domain.Toy;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Toy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ToyRepository extends MongoRepository<Toy, String> {

}

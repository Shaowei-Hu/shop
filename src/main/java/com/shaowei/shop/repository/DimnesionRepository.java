package com.shaowei.shop.repository;

import com.shaowei.shop.domain.Dimnesion;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Dimnesion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DimnesionRepository extends MongoRepository<Dimnesion, String> {

}

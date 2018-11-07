package com.shaowei.shop.service;

import com.shaowei.shop.service.dto.ToyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Toy.
 */
public interface ToyService {

    /**
     * Save a toy.
     *
     * @param toyDTO the entity to save
     * @return the persisted entity
     */
    ToyDTO save(ToyDTO toyDTO);

    /**
     * Get all the toys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ToyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" toy.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ToyDTO> findOne(String id);

    /**
     * Delete the "id" toy.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the toy corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ToyDTO> search(String query, Pageable pageable);
}

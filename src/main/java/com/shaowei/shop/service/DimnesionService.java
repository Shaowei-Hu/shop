package com.shaowei.shop.service;

import com.shaowei.shop.service.dto.DimnesionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dimnesion.
 */
public interface DimnesionService {

    /**
     * Save a dimnesion.
     *
     * @param dimnesionDTO the entity to save
     * @return the persisted entity
     */
    DimnesionDTO save(DimnesionDTO dimnesionDTO);

    /**
     * Get all the dimnesions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DimnesionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dimnesion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DimnesionDTO> findOne(String id);

    /**
     * Delete the "id" dimnesion.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the dimnesion corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DimnesionDTO> search(String query, Pageable pageable);
}

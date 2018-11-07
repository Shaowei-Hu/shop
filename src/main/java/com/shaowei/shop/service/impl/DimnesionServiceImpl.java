package com.shaowei.shop.service.impl;

import com.shaowei.shop.service.DimnesionService;
import com.shaowei.shop.domain.Dimnesion;
import com.shaowei.shop.repository.DimnesionRepository;
import com.shaowei.shop.repository.search.DimnesionSearchRepository;
import com.shaowei.shop.service.dto.DimnesionDTO;
import com.shaowei.shop.service.mapper.DimnesionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Dimnesion.
 */
@Service
public class DimnesionServiceImpl implements DimnesionService {

    private final Logger log = LoggerFactory.getLogger(DimnesionServiceImpl.class);

    private DimnesionRepository dimnesionRepository;

    private DimnesionMapper dimnesionMapper;

    private DimnesionSearchRepository dimnesionSearchRepository;

    public DimnesionServiceImpl(DimnesionRepository dimnesionRepository, DimnesionMapper dimnesionMapper, DimnesionSearchRepository dimnesionSearchRepository) {
        this.dimnesionRepository = dimnesionRepository;
        this.dimnesionMapper = dimnesionMapper;
        this.dimnesionSearchRepository = dimnesionSearchRepository;
    }

    /**
     * Save a dimnesion.
     *
     * @param dimnesionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DimnesionDTO save(DimnesionDTO dimnesionDTO) {
        log.debug("Request to save Dimnesion : {}", dimnesionDTO);

        Dimnesion dimnesion = dimnesionMapper.toEntity(dimnesionDTO);
        dimnesion = dimnesionRepository.save(dimnesion);
        DimnesionDTO result = dimnesionMapper.toDto(dimnesion);
        dimnesionSearchRepository.save(dimnesion);
        return result;
    }

    /**
     * Get all the dimnesions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DimnesionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dimnesions");
        return dimnesionRepository.findAll(pageable)
            .map(dimnesionMapper::toDto);
    }


    /**
     * Get one dimnesion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<DimnesionDTO> findOne(String id) {
        log.debug("Request to get Dimnesion : {}", id);
        return dimnesionRepository.findById(id)
            .map(dimnesionMapper::toDto);
    }

    /**
     * Delete the dimnesion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Dimnesion : {}", id);
        dimnesionRepository.deleteById(id);
        dimnesionSearchRepository.deleteById(id);
    }

    /**
     * Search for the dimnesion corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DimnesionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dimnesions for query {}", query);
        return dimnesionSearchRepository.search(queryStringQuery(query), pageable)
            .map(dimnesionMapper::toDto);
    }
}

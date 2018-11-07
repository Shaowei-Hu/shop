package com.shaowei.shop.service.impl;

import com.shaowei.shop.service.ToyService;
import com.shaowei.shop.domain.Toy;
import com.shaowei.shop.repository.ToyRepository;
import com.shaowei.shop.repository.search.ToySearchRepository;
import com.shaowei.shop.service.dto.ToyDTO;
import com.shaowei.shop.service.mapper.ToyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Toy.
 */
@Service
public class ToyServiceImpl implements ToyService {

    private final Logger log = LoggerFactory.getLogger(ToyServiceImpl.class);

    private ToyRepository toyRepository;

    private ToyMapper toyMapper;

    private ToySearchRepository toySearchRepository;

    public ToyServiceImpl(ToyRepository toyRepository, ToyMapper toyMapper, ToySearchRepository toySearchRepository) {
        this.toyRepository = toyRepository;
        this.toyMapper = toyMapper;
        this.toySearchRepository = toySearchRepository;
    }

    /**
     * Save a toy.
     *
     * @param toyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ToyDTO save(ToyDTO toyDTO) {
        log.debug("Request to save Toy : {}", toyDTO);

        Toy toy = toyMapper.toEntity(toyDTO);
        toy = toyRepository.save(toy);
        ToyDTO result = toyMapper.toDto(toy);
        toySearchRepository.save(toy);
        return result;
    }

    /**
     * Get all the toys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ToyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Toys");
        return toyRepository.findAll(pageable)
            .map(toyMapper::toDto);
    }


    /**
     * Get one toy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<ToyDTO> findOne(String id) {
        log.debug("Request to get Toy : {}", id);
        return toyRepository.findById(id)
            .map(toyMapper::toDto);
    }

    /**
     * Delete the toy by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Toy : {}", id);
        toyRepository.deleteById(id);
        toySearchRepository.deleteById(id);
    }

    /**
     * Search for the toy corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ToyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Toys for query {}", query);
        return toySearchRepository.search(queryStringQuery(query), pageable)
            .map(toyMapper::toDto);
    }
}

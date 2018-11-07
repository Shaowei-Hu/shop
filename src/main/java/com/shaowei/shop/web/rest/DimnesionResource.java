package com.shaowei.shop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shaowei.shop.service.DimnesionService;
import com.shaowei.shop.web.rest.errors.BadRequestAlertException;
import com.shaowei.shop.web.rest.util.HeaderUtil;
import com.shaowei.shop.web.rest.util.PaginationUtil;
import com.shaowei.shop.service.dto.DimnesionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Dimnesion.
 */
@RestController
@RequestMapping("/api")
public class DimnesionResource {

    private final Logger log = LoggerFactory.getLogger(DimnesionResource.class);

    private static final String ENTITY_NAME = "dimnesion";

    private DimnesionService dimnesionService;

    public DimnesionResource(DimnesionService dimnesionService) {
        this.dimnesionService = dimnesionService;
    }

    /**
     * POST  /dimnesions : Create a new dimnesion.
     *
     * @param dimnesionDTO the dimnesionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dimnesionDTO, or with status 400 (Bad Request) if the dimnesion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dimnesions")
    @Timed
    public ResponseEntity<DimnesionDTO> createDimnesion(@RequestBody DimnesionDTO dimnesionDTO) throws URISyntaxException {
        log.debug("REST request to save Dimnesion : {}", dimnesionDTO);
        if (dimnesionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dimnesion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DimnesionDTO result = dimnesionService.save(dimnesionDTO);
        return ResponseEntity.created(new URI("/api/dimnesions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dimnesions : Updates an existing dimnesion.
     *
     * @param dimnesionDTO the dimnesionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dimnesionDTO,
     * or with status 400 (Bad Request) if the dimnesionDTO is not valid,
     * or with status 500 (Internal Server Error) if the dimnesionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dimnesions")
    @Timed
    public ResponseEntity<DimnesionDTO> updateDimnesion(@RequestBody DimnesionDTO dimnesionDTO) throws URISyntaxException {
        log.debug("REST request to update Dimnesion : {}", dimnesionDTO);
        if (dimnesionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DimnesionDTO result = dimnesionService.save(dimnesionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dimnesionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dimnesions : get all the dimnesions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dimnesions in body
     */
    @GetMapping("/dimnesions")
    @Timed
    public ResponseEntity<List<DimnesionDTO>> getAllDimnesions(Pageable pageable) {
        log.debug("REST request to get a page of Dimnesions");
        Page<DimnesionDTO> page = dimnesionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dimnesions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dimnesions/:id : get the "id" dimnesion.
     *
     * @param id the id of the dimnesionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dimnesionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dimnesions/{id}")
    @Timed
    public ResponseEntity<DimnesionDTO> getDimnesion(@PathVariable String id) {
        log.debug("REST request to get Dimnesion : {}", id);
        Optional<DimnesionDTO> dimnesionDTO = dimnesionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dimnesionDTO);
    }

    /**
     * DELETE  /dimnesions/:id : delete the "id" dimnesion.
     *
     * @param id the id of the dimnesionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dimnesions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDimnesion(@PathVariable String id) {
        log.debug("REST request to delete Dimnesion : {}", id);
        dimnesionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/dimnesions?query=:query : search for the dimnesion corresponding
     * to the query.
     *
     * @param query the query of the dimnesion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dimnesions")
    @Timed
    public ResponseEntity<List<DimnesionDTO>> searchDimnesions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dimnesions for query {}", query);
        Page<DimnesionDTO> page = dimnesionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dimnesions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

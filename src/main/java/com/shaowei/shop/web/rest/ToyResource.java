package com.shaowei.shop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shaowei.shop.service.ToyService;
import com.shaowei.shop.web.rest.errors.BadRequestAlertException;
import com.shaowei.shop.web.rest.util.HeaderUtil;
import com.shaowei.shop.web.rest.util.PaginationUtil;
import com.shaowei.shop.service.dto.ToyDTO;
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
 * REST controller for managing Toy.
 */
@RestController
@RequestMapping("/api")
public class ToyResource {

    private final Logger log = LoggerFactory.getLogger(ToyResource.class);

    private static final String ENTITY_NAME = "toy";

    private ToyService toyService;

    public ToyResource(ToyService toyService) {
        this.toyService = toyService;
    }

    /**
     * POST  /toys : Create a new toy.
     *
     * @param toyDTO the toyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new toyDTO, or with status 400 (Bad Request) if the toy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/toys")
    @Timed
    public ResponseEntity<ToyDTO> createToy(@RequestBody ToyDTO toyDTO) throws URISyntaxException {
        log.debug("REST request to save Toy : {}", toyDTO);
        if (toyDTO.getId() != null) {
            throw new BadRequestAlertException("A new toy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ToyDTO result = toyService.save(toyDTO);
        return ResponseEntity.created(new URI("/api/toys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /toys : Updates an existing toy.
     *
     * @param toyDTO the toyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated toyDTO,
     * or with status 400 (Bad Request) if the toyDTO is not valid,
     * or with status 500 (Internal Server Error) if the toyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/toys")
    @Timed
    public ResponseEntity<ToyDTO> updateToy(@RequestBody ToyDTO toyDTO) throws URISyntaxException {
        log.debug("REST request to update Toy : {}", toyDTO);
        if (toyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ToyDTO result = toyService.save(toyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, toyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /toys : get all the toys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of toys in body
     */
    @GetMapping("/toys")
    @Timed
    public ResponseEntity<List<ToyDTO>> getAllToys(Pageable pageable) {
        log.debug("REST request to get a page of Toys");
        Page<ToyDTO> page = toyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/toys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /toys/:id : get the "id" toy.
     *
     * @param id the id of the toyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the toyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/toys/{id}")
    @Timed
    public ResponseEntity<ToyDTO> getToy(@PathVariable String id) {
        log.debug("REST request to get Toy : {}", id);
        Optional<ToyDTO> toyDTO = toyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(toyDTO);
    }

    /**
     * DELETE  /toys/:id : delete the "id" toy.
     *
     * @param id the id of the toyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/toys/{id}")
    @Timed
    public ResponseEntity<Void> deleteToy(@PathVariable String id) {
        log.debug("REST request to delete Toy : {}", id);
        toyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/toys?query=:query : search for the toy corresponding
     * to the query.
     *
     * @param query the query of the toy search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/toys")
    @Timed
    public ResponseEntity<List<ToyDTO>> searchToys(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Toys for query {}", query);
        Page<ToyDTO> page = toyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/toys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

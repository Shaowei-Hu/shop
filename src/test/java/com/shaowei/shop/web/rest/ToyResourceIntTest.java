package com.shaowei.shop.web.rest;

import com.shaowei.shop.ShopApp;

import com.shaowei.shop.domain.Toy;
import com.shaowei.shop.repository.ToyRepository;
import com.shaowei.shop.repository.search.ToySearchRepository;
import com.shaowei.shop.service.ToyService;
import com.shaowei.shop.service.dto.ToyDTO;
import com.shaowei.shop.service.mapper.ToyMapper;
import com.shaowei.shop.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.shaowei.shop.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ToyResource REST controller.
 *
 * @see ToyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApp.class)
public class ToyResourceIntTest {

    private static final String DEFAULT_RECOMMENDED_AGE = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ToyRepository toyRepository;

    @Autowired
    private ToyMapper toyMapper;
    
    @Autowired
    private ToyService toyService;

    /**
     * This repository is mocked in the com.shaowei.shop.repository.search test package.
     *
     * @see com.shaowei.shop.repository.search.ToySearchRepositoryMockConfiguration
     */
    @Autowired
    private ToySearchRepository mockToySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restToyMockMvc;

    private Toy toy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ToyResource toyResource = new ToyResource(toyService);
        this.restToyMockMvc = MockMvcBuilders.standaloneSetup(toyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Toy createEntity() {
        Toy toy = new Toy()
            .recommendedAge(DEFAULT_RECOMMENDED_AGE)
            .gender(DEFAULT_GENDER)
            .purchaseDate(DEFAULT_PURCHASE_DATE);
        return toy;
    }

    @Before
    public void initTest() {
        toyRepository.deleteAll();
        toy = createEntity();
    }

    @Test
    public void createToy() throws Exception {
        int databaseSizeBeforeCreate = toyRepository.findAll().size();

        // Create the Toy
        ToyDTO toyDTO = toyMapper.toDto(toy);
        restToyMockMvc.perform(post("/api/toys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(toyDTO)))
            .andExpect(status().isCreated());

        // Validate the Toy in the database
        List<Toy> toyList = toyRepository.findAll();
        assertThat(toyList).hasSize(databaseSizeBeforeCreate + 1);
        Toy testToy = toyList.get(toyList.size() - 1);
        assertThat(testToy.getRecommendedAge()).isEqualTo(DEFAULT_RECOMMENDED_AGE);
        assertThat(testToy.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testToy.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);

        // Validate the Toy in Elasticsearch
        verify(mockToySearchRepository, times(1)).save(testToy);
    }

    @Test
    public void createToyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = toyRepository.findAll().size();

        // Create the Toy with an existing ID
        toy.setId("existing_id");
        ToyDTO toyDTO = toyMapper.toDto(toy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restToyMockMvc.perform(post("/api/toys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(toyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Toy in the database
        List<Toy> toyList = toyRepository.findAll();
        assertThat(toyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Toy in Elasticsearch
        verify(mockToySearchRepository, times(0)).save(toy);
    }

    @Test
    public void getAllToys() throws Exception {
        // Initialize the database
        toyRepository.save(toy);

        // Get all the toyList
        restToyMockMvc.perform(get("/api/toys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toy.getId())))
            .andExpect(jsonPath("$.[*].recommendedAge").value(hasItem(DEFAULT_RECOMMENDED_AGE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())));
    }
    
    @Test
    public void getToy() throws Exception {
        // Initialize the database
        toyRepository.save(toy);

        // Get the toy
        restToyMockMvc.perform(get("/api/toys/{id}", toy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(toy.getId()))
            .andExpect(jsonPath("$.recommendedAge").value(DEFAULT_RECOMMENDED_AGE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()));
    }

    @Test
    public void getNonExistingToy() throws Exception {
        // Get the toy
        restToyMockMvc.perform(get("/api/toys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateToy() throws Exception {
        // Initialize the database
        toyRepository.save(toy);

        int databaseSizeBeforeUpdate = toyRepository.findAll().size();

        // Update the toy
        Toy updatedToy = toyRepository.findById(toy.getId()).get();
        updatedToy
            .recommendedAge(UPDATED_RECOMMENDED_AGE)
            .gender(UPDATED_GENDER)
            .purchaseDate(UPDATED_PURCHASE_DATE);
        ToyDTO toyDTO = toyMapper.toDto(updatedToy);

        restToyMockMvc.perform(put("/api/toys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(toyDTO)))
            .andExpect(status().isOk());

        // Validate the Toy in the database
        List<Toy> toyList = toyRepository.findAll();
        assertThat(toyList).hasSize(databaseSizeBeforeUpdate);
        Toy testToy = toyList.get(toyList.size() - 1);
        assertThat(testToy.getRecommendedAge()).isEqualTo(UPDATED_RECOMMENDED_AGE);
        assertThat(testToy.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testToy.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);

        // Validate the Toy in Elasticsearch
        verify(mockToySearchRepository, times(1)).save(testToy);
    }

    @Test
    public void updateNonExistingToy() throws Exception {
        int databaseSizeBeforeUpdate = toyRepository.findAll().size();

        // Create the Toy
        ToyDTO toyDTO = toyMapper.toDto(toy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToyMockMvc.perform(put("/api/toys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(toyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Toy in the database
        List<Toy> toyList = toyRepository.findAll();
        assertThat(toyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Toy in Elasticsearch
        verify(mockToySearchRepository, times(0)).save(toy);
    }

    @Test
    public void deleteToy() throws Exception {
        // Initialize the database
        toyRepository.save(toy);

        int databaseSizeBeforeDelete = toyRepository.findAll().size();

        // Get the toy
        restToyMockMvc.perform(delete("/api/toys/{id}", toy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Toy> toyList = toyRepository.findAll();
        assertThat(toyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Toy in Elasticsearch
        verify(mockToySearchRepository, times(1)).deleteById(toy.getId());
    }

    @Test
    public void searchToy() throws Exception {
        // Initialize the database
        toyRepository.save(toy);
        when(mockToySearchRepository.search(queryStringQuery("id:" + toy.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(toy), PageRequest.of(0, 1), 1));
        // Search the toy
        restToyMockMvc.perform(get("/api/_search/toys?query=id:" + toy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(toy.getId())))
            .andExpect(jsonPath("$.[*].recommendedAge").value(hasItem(DEFAULT_RECOMMENDED_AGE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Toy.class);
        Toy toy1 = new Toy();
        toy1.setId("id1");
        Toy toy2 = new Toy();
        toy2.setId(toy1.getId());
        assertThat(toy1).isEqualTo(toy2);
        toy2.setId("id2");
        assertThat(toy1).isNotEqualTo(toy2);
        toy1.setId(null);
        assertThat(toy1).isNotEqualTo(toy2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ToyDTO.class);
        ToyDTO toyDTO1 = new ToyDTO();
        toyDTO1.setId("id1");
        ToyDTO toyDTO2 = new ToyDTO();
        assertThat(toyDTO1).isNotEqualTo(toyDTO2);
        toyDTO2.setId(toyDTO1.getId());
        assertThat(toyDTO1).isEqualTo(toyDTO2);
        toyDTO2.setId("id2");
        assertThat(toyDTO1).isNotEqualTo(toyDTO2);
        toyDTO1.setId(null);
        assertThat(toyDTO1).isNotEqualTo(toyDTO2);
    }
}

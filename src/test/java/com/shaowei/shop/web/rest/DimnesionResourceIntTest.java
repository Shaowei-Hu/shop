package com.shaowei.shop.web.rest;

import com.shaowei.shop.ShopApp;

import com.shaowei.shop.domain.Dimnesion;
import com.shaowei.shop.repository.DimnesionRepository;
import com.shaowei.shop.repository.search.DimnesionSearchRepository;
import com.shaowei.shop.service.DimnesionService;
import com.shaowei.shop.service.dto.DimnesionDTO;
import com.shaowei.shop.service.mapper.DimnesionMapper;
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
 * Test class for the DimnesionResource REST controller.
 *
 * @see DimnesionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApp.class)
public class DimnesionResourceIntTest {

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    @Autowired
    private DimnesionRepository dimnesionRepository;

    @Autowired
    private DimnesionMapper dimnesionMapper;
    
    @Autowired
    private DimnesionService dimnesionService;

    /**
     * This repository is mocked in the com.shaowei.shop.repository.search test package.
     *
     * @see com.shaowei.shop.repository.search.DimnesionSearchRepositoryMockConfiguration
     */
    @Autowired
    private DimnesionSearchRepository mockDimnesionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDimnesionMockMvc;

    private Dimnesion dimnesion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DimnesionResource dimnesionResource = new DimnesionResource(dimnesionService);
        this.restDimnesionMockMvc = MockMvcBuilders.standaloneSetup(dimnesionResource)
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
    public static Dimnesion createEntity() {
        Dimnesion dimnesion = new Dimnesion()
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT);
        return dimnesion;
    }

    @Before
    public void initTest() {
        dimnesionRepository.deleteAll();
        dimnesion = createEntity();
    }

    @Test
    public void createDimnesion() throws Exception {
        int databaseSizeBeforeCreate = dimnesionRepository.findAll().size();

        // Create the Dimnesion
        DimnesionDTO dimnesionDTO = dimnesionMapper.toDto(dimnesion);
        restDimnesionMockMvc.perform(post("/api/dimnesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimnesionDTO)))
            .andExpect(status().isCreated());

        // Validate the Dimnesion in the database
        List<Dimnesion> dimnesionList = dimnesionRepository.findAll();
        assertThat(dimnesionList).hasSize(databaseSizeBeforeCreate + 1);
        Dimnesion testDimnesion = dimnesionList.get(dimnesionList.size() - 1);
        assertThat(testDimnesion.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testDimnesion.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testDimnesion.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testDimnesion.getWeight()).isEqualTo(DEFAULT_WEIGHT);

        // Validate the Dimnesion in Elasticsearch
        verify(mockDimnesionSearchRepository, times(1)).save(testDimnesion);
    }

    @Test
    public void createDimnesionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dimnesionRepository.findAll().size();

        // Create the Dimnesion with an existing ID
        dimnesion.setId("existing_id");
        DimnesionDTO dimnesionDTO = dimnesionMapper.toDto(dimnesion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimnesionMockMvc.perform(post("/api/dimnesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimnesionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dimnesion in the database
        List<Dimnesion> dimnesionList = dimnesionRepository.findAll();
        assertThat(dimnesionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dimnesion in Elasticsearch
        verify(mockDimnesionSearchRepository, times(0)).save(dimnesion);
    }

    @Test
    public void getAllDimnesions() throws Exception {
        // Initialize the database
        dimnesionRepository.save(dimnesion);

        // Get all the dimnesionList
        restDimnesionMockMvc.perform(get("/api/dimnesions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimnesion.getId())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())));
    }
    
    @Test
    public void getDimnesion() throws Exception {
        // Initialize the database
        dimnesionRepository.save(dimnesion);

        // Get the dimnesion
        restDimnesionMockMvc.perform(get("/api/dimnesions/{id}", dimnesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dimnesion.getId()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()));
    }

    @Test
    public void getNonExistingDimnesion() throws Exception {
        // Get the dimnesion
        restDimnesionMockMvc.perform(get("/api/dimnesions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDimnesion() throws Exception {
        // Initialize the database
        dimnesionRepository.save(dimnesion);

        int databaseSizeBeforeUpdate = dimnesionRepository.findAll().size();

        // Update the dimnesion
        Dimnesion updatedDimnesion = dimnesionRepository.findById(dimnesion.getId()).get();
        updatedDimnesion
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT);
        DimnesionDTO dimnesionDTO = dimnesionMapper.toDto(updatedDimnesion);

        restDimnesionMockMvc.perform(put("/api/dimnesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimnesionDTO)))
            .andExpect(status().isOk());

        // Validate the Dimnesion in the database
        List<Dimnesion> dimnesionList = dimnesionRepository.findAll();
        assertThat(dimnesionList).hasSize(databaseSizeBeforeUpdate);
        Dimnesion testDimnesion = dimnesionList.get(dimnesionList.size() - 1);
        assertThat(testDimnesion.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testDimnesion.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testDimnesion.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testDimnesion.getWeight()).isEqualTo(UPDATED_WEIGHT);

        // Validate the Dimnesion in Elasticsearch
        verify(mockDimnesionSearchRepository, times(1)).save(testDimnesion);
    }

    @Test
    public void updateNonExistingDimnesion() throws Exception {
        int databaseSizeBeforeUpdate = dimnesionRepository.findAll().size();

        // Create the Dimnesion
        DimnesionDTO dimnesionDTO = dimnesionMapper.toDto(dimnesion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimnesionMockMvc.perform(put("/api/dimnesions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimnesionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dimnesion in the database
        List<Dimnesion> dimnesionList = dimnesionRepository.findAll();
        assertThat(dimnesionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dimnesion in Elasticsearch
        verify(mockDimnesionSearchRepository, times(0)).save(dimnesion);
    }

    @Test
    public void deleteDimnesion() throws Exception {
        // Initialize the database
        dimnesionRepository.save(dimnesion);

        int databaseSizeBeforeDelete = dimnesionRepository.findAll().size();

        // Get the dimnesion
        restDimnesionMockMvc.perform(delete("/api/dimnesions/{id}", dimnesion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dimnesion> dimnesionList = dimnesionRepository.findAll();
        assertThat(dimnesionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dimnesion in Elasticsearch
        verify(mockDimnesionSearchRepository, times(1)).deleteById(dimnesion.getId());
    }

    @Test
    public void searchDimnesion() throws Exception {
        // Initialize the database
        dimnesionRepository.save(dimnesion);
        when(mockDimnesionSearchRepository.search(queryStringQuery("id:" + dimnesion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dimnesion), PageRequest.of(0, 1), 1));
        // Search the dimnesion
        restDimnesionMockMvc.perform(get("/api/_search/dimnesions?query=id:" + dimnesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimnesion.getId())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dimnesion.class);
        Dimnesion dimnesion1 = new Dimnesion();
        dimnesion1.setId("id1");
        Dimnesion dimnesion2 = new Dimnesion();
        dimnesion2.setId(dimnesion1.getId());
        assertThat(dimnesion1).isEqualTo(dimnesion2);
        dimnesion2.setId("id2");
        assertThat(dimnesion1).isNotEqualTo(dimnesion2);
        dimnesion1.setId(null);
        assertThat(dimnesion1).isNotEqualTo(dimnesion2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DimnesionDTO.class);
        DimnesionDTO dimnesionDTO1 = new DimnesionDTO();
        dimnesionDTO1.setId("id1");
        DimnesionDTO dimnesionDTO2 = new DimnesionDTO();
        assertThat(dimnesionDTO1).isNotEqualTo(dimnesionDTO2);
        dimnesionDTO2.setId(dimnesionDTO1.getId());
        assertThat(dimnesionDTO1).isEqualTo(dimnesionDTO2);
        dimnesionDTO2.setId("id2");
        assertThat(dimnesionDTO1).isNotEqualTo(dimnesionDTO2);
        dimnesionDTO1.setId(null);
        assertThat(dimnesionDTO1).isNotEqualTo(dimnesionDTO2);
    }
}

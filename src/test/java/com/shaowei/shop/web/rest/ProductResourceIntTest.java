package com.shaowei.shop.web.rest;

import com.shaowei.shop.ShopApp;

import com.shaowei.shop.domain.Product;
import com.shaowei.shop.repository.ProductRepository;
import com.shaowei.shop.repository.search.ProductSearchRepository;
import com.shaowei.shop.service.ProductService;
import com.shaowei.shop.service.dto.ProductDTO;
import com.shaowei.shop.service.mapper.ProductMapper;
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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_RELEASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RELEASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURE_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURE_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_METERIALS = "AAAAAAAAAA";
    private static final String UPDATED_METERIALS = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_URL = "BBBBBBBBBB";

    private static final Double DEFAULT_ORIGINAL_PRICE = 1D;
    private static final Double UPDATED_ORIGINAL_PRICE = 2D;

    private static final Double DEFAULT_ACTUAL_PRICE = 1D;
    private static final Double UPDATED_ACTUAL_PRICE = 2D;

    private static final Boolean DEFAULT_GARANTIE = false;
    private static final Boolean UPDATED_GARANTIE = true;

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ProductService productService;

    /**
     * This repository is mocked in the com.shaowei.shop.repository.search test package.
     *
     * @see com.shaowei.shop.repository.search.ProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductSearchRepository mockProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
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
    public static Product createEntity() {
        Product product = new Product()
            .brand(DEFAULT_BRAND)
            .name(DEFAULT_NAME)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .comment(DEFAULT_COMMENT)
            .manufactureOrigin(DEFAULT_MANUFACTURE_ORIGIN)
            .meterials(DEFAULT_METERIALS)
            .externalUrl(DEFAULT_EXTERNAL_URL)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .actualPrice(DEFAULT_ACTUAL_PRICE)
            .garantie(DEFAULT_GARANTIE)
            .photo(DEFAULT_PHOTO)
            .state(DEFAULT_STATE)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return product;
    }

    @Before
    public void initTest() {
        productRepository.deleteAll();
        product = createEntity();
    }

    @Test
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testProduct.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testProduct.getManufactureOrigin()).isEqualTo(DEFAULT_MANUFACTURE_ORIGIN);
        assertThat(testProduct.getMeterials()).isEqualTo(DEFAULT_METERIALS);
        assertThat(testProduct.getExternalUrl()).isEqualTo(DEFAULT_EXTERNAL_URL);
        assertThat(testProduct.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testProduct.getActualPrice()).isEqualTo(DEFAULT_ACTUAL_PRICE);
        assertThat(testProduct.isGarantie()).isEqualTo(DEFAULT_GARANTIE);
        assertThat(testProduct.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProduct.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testProduct.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testProduct.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId("existing_id");
        ProductDTO productDTO = productMapper.toDto(product);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].manufactureOrigin").value(hasItem(DEFAULT_MANUFACTURE_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].meterials").value(hasItem(DEFAULT_METERIALS.toString())))
            .andExpect(jsonPath("$.[*].externalUrl").value(hasItem(DEFAULT_EXTERNAL_URL.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].actualPrice").value(hasItem(DEFAULT_ACTUAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].garantie").value(hasItem(DEFAULT_GARANTIE.booleanValue())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.manufactureOrigin").value(DEFAULT_MANUFACTURE_ORIGIN.toString()))
            .andExpect(jsonPath("$.meterials").value(DEFAULT_METERIALS.toString()))
            .andExpect(jsonPath("$.externalUrl").value(DEFAULT_EXTERNAL_URL.toString()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.actualPrice").value(DEFAULT_ACTUAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.garantie").value(DEFAULT_GARANTIE.booleanValue()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }

    @Test
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        updatedProduct
            .brand(UPDATED_BRAND)
            .name(UPDATED_NAME)
            .releaseDate(UPDATED_RELEASE_DATE)
            .comment(UPDATED_COMMENT)
            .manufactureOrigin(UPDATED_MANUFACTURE_ORIGIN)
            .meterials(UPDATED_METERIALS)
            .externalUrl(UPDATED_EXTERNAL_URL)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .actualPrice(UPDATED_ACTUAL_PRICE)
            .garantie(UPDATED_GARANTIE)
            .photo(UPDATED_PHOTO)
            .state(UPDATED_STATE)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProduct.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testProduct.getManufactureOrigin()).isEqualTo(UPDATED_MANUFACTURE_ORIGIN);
        assertThat(testProduct.getMeterials()).isEqualTo(UPDATED_METERIALS);
        assertThat(testProduct.getExternalUrl()).isEqualTo(UPDATED_EXTERNAL_URL);
        assertThat(testProduct.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testProduct.getActualPrice()).isEqualTo(UPDATED_ACTUAL_PRICE);
        assertThat(testProduct.isGarantie()).isEqualTo(UPDATED_GARANTIE);
        assertThat(testProduct.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProduct.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testProduct.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testProduct.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).save(testProduct);
    }

    @Test
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(0)).save(product);
    }

    @Test
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Product in Elasticsearch
        verify(mockProductSearchRepository, times(1)).deleteById(product.getId());
    }

    @Test
    public void searchProduct() throws Exception {
        // Initialize the database
        productRepository.save(product);
        when(mockProductSearchRepository.search(queryStringQuery("id:" + product.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(product), PageRequest.of(0, 1), 1));
        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].manufactureOrigin").value(hasItem(DEFAULT_MANUFACTURE_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].meterials").value(hasItem(DEFAULT_METERIALS.toString())))
            .andExpect(jsonPath("$.[*].externalUrl").value(hasItem(DEFAULT_EXTERNAL_URL.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].actualPrice").value(hasItem(DEFAULT_ACTUAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].garantie").value(hasItem(DEFAULT_GARANTIE.booleanValue())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId("id1");
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId("id2");
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDTO.class);
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId("id1");
        ProductDTO productDTO2 = new ProductDTO();
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO2.setId(productDTO1.getId());
        assertThat(productDTO1).isEqualTo(productDTO2);
        productDTO2.setId("id2");
        assertThat(productDTO1).isNotEqualTo(productDTO2);
        productDTO1.setId(null);
        assertThat(productDTO1).isNotEqualTo(productDTO2);
    }
}

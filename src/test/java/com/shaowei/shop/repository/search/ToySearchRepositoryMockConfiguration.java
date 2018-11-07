package com.shaowei.shop.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ToySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ToySearchRepositoryMockConfiguration {

    @MockBean
    private ToySearchRepository mockToySearchRepository;

}

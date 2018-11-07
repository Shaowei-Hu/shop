package com.shaowei.shop.repository.search;

import com.shaowei.shop.domain.Toy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Toy entity.
 */
public interface ToySearchRepository extends ElasticsearchRepository<Toy, String> {
}

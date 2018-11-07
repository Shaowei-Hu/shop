package com.shaowei.shop.repository.search;

import com.shaowei.shop.domain.Dimnesion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dimnesion entity.
 */
public interface DimnesionSearchRepository extends ElasticsearchRepository<Dimnesion, String> {
}

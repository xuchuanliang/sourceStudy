package com.ant.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionDao extends ElasticsearchRepository<Product,Long> {
}

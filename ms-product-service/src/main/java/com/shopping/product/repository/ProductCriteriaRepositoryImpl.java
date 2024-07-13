package com.shopping.product.repository;

import com.shopping.product.dto.ProductSearchCriteria;
import com.shopping.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository {
  private final MongoTemplate mongoTemplate;

  public ProductCriteriaRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Page<ProductEntity> findAllWithFilters(Pageable pageable, ProductSearchCriteria criteria) {
    var query = constructQueryFromFilter(criteria);

    var products = mongoTemplate.find(query, ProductEntity.class);
    return PageableExecutionUtils.getPage(
        products, pageable, () -> mongoTemplate.count(query, ProductEntity.class));
  }
}

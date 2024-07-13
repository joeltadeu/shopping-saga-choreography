package com.shopping.product.repository;

import com.shopping.product.dto.ProductSearchCriteria;
import com.shopping.product.entity.ProductEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.Map;

public interface ProductCriteriaRepository {

  Page<ProductEntity> findAllWithFilters(Pageable pageable, ProductSearchCriteria criteria);

  default Query constructQueryFromFilter(ProductSearchCriteria criteria) {
    if (criteria == null) return new Query();

    Map<String, Criteria> criteriaMap = new HashMap<>();
    if (criteria.name() != null) {
      criteriaMap.put("name", Criteria.where("name").alike(Example.of(criteria.name())));
    }

    if (criteria.name() != null) {
      criteriaMap.put("category", Criteria.where("category").is(criteria.category()));
    }
    Query query = new Query();
    criteriaMap.values().forEach(query::addCriteria);
    return query;
  }
}

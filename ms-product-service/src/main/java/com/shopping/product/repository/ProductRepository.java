package com.shopping.product.repository;

import com.shopping.product.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, Long>, ProductCriteriaRepository {
}

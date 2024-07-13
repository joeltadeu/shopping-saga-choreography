package com.shopping.product.service;

import com.shopping.lib.commons.exception.DataNotFoundException;
import com.shopping.lib.commons.validation.RestPreConditions;
import com.shopping.product.dto.ProductSearchCriteria;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public ProductEntity findById(Long id) {
    return RestPreConditions.checkNotNull(
        repository.findById(id), "Product with Id %s was not found", id);
  }

  public Page<ProductEntity> findAll(Pageable page, ProductSearchCriteria criteria) {
    return repository.findAllWithFilters(page, criteria);
  }

  public ProductEntity insert(ProductEntity entity) {
    return repository.save(entity);
  }

  public void update(ProductEntity entity, Long id) {
    if (!repository.existsById(id)) {
      throw new DataNotFoundException("Product with Id %s was not found".formatted(id));
    }
    entity.setId(id);
    repository.save(entity);
  }

  public void delete(Long id) {
    final var foundBook = findById(id);
    repository.delete(foundBook);
  }
}

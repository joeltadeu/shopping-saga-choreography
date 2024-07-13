package com.shopping.product.dto;

import com.shopping.product.entity.ProductCategory;
import com.shopping.product.entity.ProductEntity;
import java.math.BigDecimal;

public record Product(String name, ProductCategory category, Integer quantity, BigDecimal price) {
  public static ProductEntity toEntity(Product product) {
    var entity = new ProductEntity();
    entity.setName(product.name());
    entity.setPrice(product.price());
    entity.setQuantity(product.quantity());
    entity.setCategory(product.category());
    return entity;
  }
}

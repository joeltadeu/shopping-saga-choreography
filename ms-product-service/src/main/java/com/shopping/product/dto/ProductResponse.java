package com.shopping.product.dto;

import com.shopping.product.entity.ProductCategory;
import com.shopping.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public record ProductResponse(
    Long id,
    LocalDateTime createdAt,
    String name,
    ProductCategory category,
    Integer quantity,
    BigDecimal price) {

  public static ProductResponse toModel(ProductEntity product) {
    return new ProductResponse(
        product.getId(),
        product.getCreatedAt(),
        product.getName(),
        product.getCategory(),
        product.getQuantity(),
        product.getPrice());
  }

  public static Page<ProductResponse> toModel(Page<ProductEntity> productPage) {
    List<ProductResponse> products =
        productPage.stream().map(ProductResponse::toModel).collect(toList());
    return new PageImpl<>(products, productPage.getPageable(), productPage.getTotalElements());
  }
}

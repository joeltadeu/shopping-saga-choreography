package com.shopping.product.dto;

import com.shopping.product.entity.ProductCategory;

public record ProductSearchCriteria(String name, ProductCategory category) {}

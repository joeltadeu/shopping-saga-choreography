package com.shopping.lib.commons.model;

import java.math.BigDecimal;

public record OrderItem(Long productId, Integer quantity, BigDecimal price) {}

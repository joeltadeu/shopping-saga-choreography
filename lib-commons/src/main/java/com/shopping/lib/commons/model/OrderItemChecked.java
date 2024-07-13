package com.shopping.lib.commons.model;

import com.shopping.lib.commons.model.enums.OrderItemCheckedStatus;

public record OrderItemChecked(Long productId, OrderItemCheckedStatus status) {}

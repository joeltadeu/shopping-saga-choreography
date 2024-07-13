package com.shopping.order.dto;

import com.shopping.order.entity.OrderEntity;
import com.shopping.order.entity.OrderItemEntity;
import com.shopping.lib.commons.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public record OrderResponse(
    Long orderId,
    Long customerId,
    OrderStatus status,
    LocalDateTime createdAt,
    List<OrderItemEntity> items) {

  public static OrderResponse toModel(OrderEntity entity) {
    return new OrderResponse(
        entity.getId(),
        entity.getCustomerId(),
        entity.getStatus(),
        entity.getCreatedAt(),
        entity.getItems());
  }

  public static Page<OrderResponse> toModel(Page<OrderEntity> productPage) {
    List<OrderResponse> products =
            productPage.stream().map(OrderResponse::toModel).collect(toList());
    return new PageImpl<>(products, productPage.getPageable(), productPage.getTotalElements());
  }
}

package com.shopping.order.service.mapper;

import com.shopping.lib.commons.model.OrderItem;
import com.shopping.lib.commons.model.Payment;
import com.shopping.lib.commons.model.events.Event;
import com.shopping.lib.commons.model.events.OrderCreatedEvent;
import com.shopping.order.entity.OrderEntity;
import com.shopping.order.entity.OrderItemEntity;
import java.util.List;

public class OrderEventMapper {

  private static List<OrderItem> getOrderItems(List<OrderItemEntity> items) {
    return items.stream().map(item -> new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice())).toList();
  }

  public static Event toOrderCreatedEvent(OrderEntity entity, Payment payment) {
    return OrderCreatedEvent.builder()
            .orderId(entity.getId())
            .total(entity.getTotal())
            .payment(payment)
            .items(getOrderItems(entity.getItems()))
            .customerId(entity.getCustomerId())
            .build();
  }
}

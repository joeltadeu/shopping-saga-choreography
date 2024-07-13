package com.shopping.product.service.mapper;

import com.shopping.lib.commons.model.events.InventoryDeclinedEvent;
import com.shopping.lib.commons.model.events.InventoryDeductedEvent;
import com.shopping.lib.commons.model.events.InventoryRestoredEvent;
import com.shopping.lib.commons.model.events.OrderCreatedEvent;

public class InventoryEventMapper {

  public static InventoryDeductedEvent toInventoryDeductedEvent(OrderCreatedEvent event) {
    return InventoryDeductedEvent.builder()
        .orderId(event.getOrderId())
        .total(event.getTotal())
        .items(event.getItems())
        .payment(event.getPayment())
        .build();
  }

  public static InventoryDeclinedEvent toInventoryDeclinedEvent(Long orderId) {
    return InventoryDeclinedEvent.builder().orderId(orderId).build();
  }

  public static InventoryRestoredEvent toInventoryRestoredEvent(Long orderId) {
    return InventoryRestoredEvent.builder().orderId(orderId).build();
  }
}

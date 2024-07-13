package com.shopping.payment.service.mapper;

import com.shopping.lib.commons.model.events.InventoryDeductedEvent;
import com.shopping.lib.commons.model.events.OrderCompletedEvent;
import com.shopping.lib.commons.model.events.PaymentDeclinedEvent;

public class PaymentEventMapper {
  public static PaymentDeclinedEvent toPaymentDeclinedEvent(InventoryDeductedEvent event) {
    return PaymentDeclinedEvent.builder()
        .items(event.getItems())
        .orderId(event.getOrderId())
        .build();
  }

  public static OrderCompletedEvent toOrderCompleted(Long orderId) {
    return OrderCompletedEvent.builder().orderId(orderId).build();
  }
}

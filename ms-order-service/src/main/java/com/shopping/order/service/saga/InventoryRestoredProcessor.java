package com.shopping.order.service.saga;

import com.shopping.lib.commons.model.events.*;
import com.shopping.order.service.saga.state.OrderCancelled;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class InventoryRestoredProcessor {

  private final OrderCancelled state;

  public void handle(InventoryRestoredEvent event) {
    state.process(event.getOrderId());
  }
}

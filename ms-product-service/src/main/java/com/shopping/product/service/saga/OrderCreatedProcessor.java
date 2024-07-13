package com.shopping.product.service.saga;

import com.shopping.lib.commons.model.events.OrderCreatedEvent;
import com.shopping.product.service.saga.state.InventoryDeducted;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrderCreatedProcessor {

  private final InventoryDeducted state;

  public void handle(OrderCreatedEvent event) {
    state.process(event);
  }
}

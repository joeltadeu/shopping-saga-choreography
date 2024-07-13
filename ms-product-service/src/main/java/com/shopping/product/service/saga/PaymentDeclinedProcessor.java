package com.shopping.product.service.saga;

import com.shopping.lib.commons.model.events.PaymentDeclinedEvent;
import com.shopping.product.service.saga.state.InventoryDeclined;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentDeclinedProcessor {

  private final InventoryDeclined state;

  public void handle(PaymentDeclinedEvent event) {
    state.process(event);
  }
}

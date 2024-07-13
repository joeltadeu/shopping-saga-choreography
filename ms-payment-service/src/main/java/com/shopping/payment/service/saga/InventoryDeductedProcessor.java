package com.shopping.payment.service.saga;

import com.shopping.lib.commons.model.events.InventoryDeductedEvent;
import com.shopping.payment.service.saga.state.PaymentProcessed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class InventoryDeductedProcessor {
  private final PaymentProcessed state;

  public void handle(InventoryDeductedEvent event) {
    state.process(event);
  }
}

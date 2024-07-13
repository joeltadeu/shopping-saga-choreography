package com.shopping.order.service.saga;

import com.shopping.lib.commons.model.events.*;
import com.shopping.order.service.saga.state.OrderCompleted;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrderCompletedProcessor {

  private final OrderCompleted state;

  public void handle(OrderCompletedEvent event) {
    state.process(event.getOrderId());
  }
}

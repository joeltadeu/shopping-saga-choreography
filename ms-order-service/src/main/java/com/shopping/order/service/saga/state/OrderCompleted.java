package com.shopping.order.service.saga.state;

import com.shopping.lib.commons.model.enums.OrderStatus;
import com.shopping.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrderCompleted {
  private final OrderRepository repository;

  public void process(Long id) {
    repository
        .findById(id)
        .ifPresentOrElse(
            order -> {
              order.setStatus(OrderStatus.COMPLETED);
              repository.save(order);
            },
            () -> log.error("Order Id {} was not found", id));
  }
}

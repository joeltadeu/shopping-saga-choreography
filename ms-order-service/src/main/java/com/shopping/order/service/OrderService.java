package com.shopping.order.service;

import com.shopping.lib.commons.model.Payment;
import com.shopping.lib.commons.validation.RestPreConditions;
import com.shopping.order.dto.Order;
import com.shopping.order.entity.OrderEntity;
import com.shopping.order.repository.OrderRepository;
import com.shopping.order.service.mapper.OrderEventMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.shopping.lib.commons.util.ShoppingQueue.ORDER_CREATED_QUEUE;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository repository;
  private final RabbitTemplate rabbitTemplate;

  public OrderEntity findById(Long customerId, Long orderId) {
    return RestPreConditions.checkNotNull(
        repository.findByCustomerIdAndId(customerId, orderId),
        "Order with Id %s was not found",
        orderId);
  }

  public void save(OrderEntity entity, Payment payment) {
    repository.save(entity);
    rabbitTemplate.convertAndSend(
        ORDER_CREATED_QUEUE, OrderEventMapper.toOrderCreatedEvent(entity, payment));
  }

  public Page<OrderEntity> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
    return repository.findAllByCustomerId(customerId, pageRequest);
  }
}

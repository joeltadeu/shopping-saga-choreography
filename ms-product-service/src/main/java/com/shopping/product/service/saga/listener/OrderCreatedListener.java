package com.shopping.product.service.saga.listener;

import static com.shopping.lib.commons.util.ShoppingQueue.ORDER_CREATED_QUEUE;

import com.shopping.lib.commons.model.events.OrderCreatedEvent;
import com.shopping.product.service.saga.OrderCreatedProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrderCreatedListener {
  private final OrderCreatedProcessor processor;

  @RabbitListener(queues = ORDER_CREATED_QUEUE)
  public void listen(Message<OrderCreatedEvent> message) {
    log.info("Message consumed: {}", message);
    processor.handle(message.getPayload());
  }
}

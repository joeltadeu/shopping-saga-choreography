package com.shopping.order.service.saga.listener;

import static com.shopping.lib.commons.util.ShoppingQueue.INVENTORY_DECLINED_QUEUE;

import com.shopping.lib.commons.model.events.InventoryDeclinedEvent;
import com.shopping.order.service.saga.InventoryDeclinedProcessor;
import com.shopping.order.service.saga.InventoryRestoredProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class InventoryDeclinedListener {
  private final InventoryDeclinedProcessor processor;

  @RabbitListener(queues = INVENTORY_DECLINED_QUEUE)
  public void processInventoryDeclined(Message<InventoryDeclinedEvent> message) {
    log.info("Message consumed: {}", message);
    processor.handle(message.getPayload());
  }
}

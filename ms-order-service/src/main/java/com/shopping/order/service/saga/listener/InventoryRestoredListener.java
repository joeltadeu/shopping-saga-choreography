package com.shopping.order.service.saga.listener;

import static com.shopping.lib.commons.util.ShoppingQueue.INVENTORY_RESTORED_QUEUE;

import com.shopping.lib.commons.model.events.InventoryRestoredEvent;
import com.shopping.order.service.saga.InventoryRestoredProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class InventoryRestoredListener {
  private final InventoryRestoredProcessor processor;

  @RabbitListener(queues = INVENTORY_RESTORED_QUEUE)
  public void processInventoryRestored(Message<InventoryRestoredEvent> message) {
    log.info("Message consumed: {}", message);
    processor.handle(message.getPayload());
  }
}

package com.shopping.payment.service.saga.listener;

import static com.shopping.lib.commons.util.ShoppingQueue.INVENTORY_DEDUCTED_QUEUE;

import com.shopping.lib.commons.model.events.InventoryDeductedEvent;
import com.shopping.payment.service.saga.InventoryDeductedProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class InventoryDeductedListener {
  private final InventoryDeductedProcessor processor;

  @RabbitListener(queues = INVENTORY_DEDUCTED_QUEUE)
  public void listen(Message<InventoryDeductedEvent> message) {
    log.info("Message consumed: {}", message);
    processor.handle(message.getPayload());
  }
}

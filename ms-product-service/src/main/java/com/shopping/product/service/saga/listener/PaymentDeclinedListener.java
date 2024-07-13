package com.shopping.product.service.saga.listener;

import static com.shopping.lib.commons.util.ShoppingQueue.PAYMENT_DECLINED_QUEUE;

import com.shopping.lib.commons.model.events.PaymentDeclinedEvent;
import com.shopping.product.service.saga.PaymentDeclinedProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentDeclinedListener {
  private final PaymentDeclinedProcessor processor;

  @RabbitListener(queues = PAYMENT_DECLINED_QUEUE)
  public void listen(Message<PaymentDeclinedEvent> message) {
    log.info("Message consumed: {}", message);
    processor.handle(message.getPayload());
  }
}

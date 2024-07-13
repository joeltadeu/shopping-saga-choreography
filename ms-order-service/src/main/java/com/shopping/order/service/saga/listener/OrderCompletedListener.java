package com.shopping.order.service.saga.listener;

import static com.shopping.lib.commons.util.ShoppingQueue.ORDER_COMPLETED_QUEUE;

import com.shopping.lib.commons.model.events.OrderCompletedEvent;
import com.shopping.order.service.saga.OrderCompletedProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderCompletedListener {
    private final OrderCompletedProcessor processor;

    @RabbitListener(queues = ORDER_COMPLETED_QUEUE)
    public void processOrderCompleted(Message<OrderCompletedEvent> message) {
        log.info("Message consumed: {}", message);
        processor.handle(message.getPayload());
    }
}

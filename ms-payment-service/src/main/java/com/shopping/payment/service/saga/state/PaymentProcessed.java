package com.shopping.payment.service.saga.state;

import static com.shopping.lib.commons.model.enums.PaymentStatus.SUCCEEDED;
import static com.shopping.lib.commons.util.ShoppingQueue.ORDER_COMPLETED_QUEUE;
import static com.shopping.lib.commons.util.ShoppingQueue.PAYMENT_DECLINED_QUEUE;

import com.shopping.lib.commons.model.events.InventoryDeductedEvent;
import com.shopping.payment.entity.PaymentOrderEntity;
import com.shopping.payment.repository.PaymentOrderRepository;
import com.shopping.payment.service.broker.BrokerService;
import com.shopping.payment.service.mapper.PaymentEventMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentProcessed {
  public final PaymentOrderRepository repository;
  public final BrokerService brokerService;
  private final RabbitTemplate rabbitTemplate;

  public void process(InventoryDeductedEvent event) {
    var paymentToken = brokerService.token(event.getOrderId(), event.getPayment());
    var paymentCharge =
        brokerService.charge(event.getOrderId(), event.getTotal(), paymentToken.getToken());

    var entity =
        PaymentOrderEntity.toEntity(
            event.getOrderId(), event.getTotal(), paymentCharge, paymentToken);

    repository.save(entity);

    if (SUCCEEDED.equals(entity.getStatus())) {
      notifyOrderCompleted(entity.getOrderId());
    } else {
      notifyPaymentDeclined(event);
    }
  }

  private void notifyPaymentDeclined(InventoryDeductedEvent event) {
    rabbitTemplate.convertAndSend(
        PAYMENT_DECLINED_QUEUE, PaymentEventMapper.toPaymentDeclinedEvent(event));
  }

  private void notifyOrderCompleted(Long orderId) {
    rabbitTemplate.convertAndSend(
        ORDER_COMPLETED_QUEUE, PaymentEventMapper.toOrderCompleted(orderId));
  }
}

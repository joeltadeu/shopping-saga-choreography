package com.shopping.order.repository.events;

import com.shopping.lib.commons.service.SequenceService;
import com.shopping.order.entity.OrderEntity;
import com.shopping.lib.commons.model.enums.OrderStatus;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderEntityListener extends AbstractMongoEventListener<OrderEntity> {

  private final SequenceService sequenceGenerator;

  public OrderEntityListener(SequenceService sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<OrderEntity> event) {
    if (event.getSource().getId() == null) {
      event.getSource().setId(sequenceGenerator.generateSequence(OrderEntity.SEQUENCE_NAME));
      event.getSource().setCreatedAt(LocalDateTime.now());
      event.getSource().setStatus(OrderStatus.PENDING);
    }
  }
}

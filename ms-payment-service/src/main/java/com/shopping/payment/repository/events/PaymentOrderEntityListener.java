package com.shopping.payment.repository.events;

import com.shopping.lib.commons.service.SequenceService;
import com.shopping.payment.entity.PaymentOrderEntity;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderEntityListener extends AbstractMongoEventListener<PaymentOrderEntity> {

  private final SequenceService sequenceGenerator;

  public PaymentOrderEntityListener(SequenceService sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<PaymentOrderEntity> event) {
    if (event.getSource().getId() == null) {
      event.getSource().setId(sequenceGenerator.generateSequence(PaymentOrderEntity.SEQUENCE_NAME));
      event.getSource().setCreatedAt(LocalDateTime.now());
    }
  }
}

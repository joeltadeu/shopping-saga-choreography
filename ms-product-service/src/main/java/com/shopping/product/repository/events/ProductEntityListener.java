package com.shopping.product.repository.events;

import com.shopping.lib.commons.service.SequenceService;

import com.shopping.product.entity.ProductEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductEntityListener extends AbstractMongoEventListener<ProductEntity> {

  private final SequenceService sequenceGenerator;

  public ProductEntityListener(SequenceService sequenceGenerator) {
    this.sequenceGenerator = sequenceGenerator;
  }

  @Override
  public void onBeforeConvert(BeforeConvertEvent<ProductEntity> event) {
    if (event.getSource().getId() == null) {
      event.getSource().setId(sequenceGenerator.generateSequence(ProductEntity.SEQUENCE_NAME));
      event.getSource().setCreatedAt(LocalDateTime.now());
    }
  }
}

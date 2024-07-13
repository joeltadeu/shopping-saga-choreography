package com.shopping.product.service.saga.state;

import static com.shopping.lib.commons.util.ShoppingQueue.INVENTORY_RESTORED_QUEUE;

import com.shopping.lib.commons.model.events.PaymentDeclinedEvent;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.repository.ProductRepository;
import com.shopping.product.service.mapper.InventoryEventMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class InventoryDeclined {
  private final ProductRepository repository;
  private final RabbitTemplate rabbitTemplate;

  public void process(PaymentDeclinedEvent event) {
    List<ProductEntity> updateProducts = new ArrayList<>();
    event
        .getItems()
        .forEach(
            item ->
                repository
                    .findById(item.productId())
                    .ifPresentOrElse(
                        product -> {
                          product.setQuantity(product.getQuantity() + item.quantity());
                          updateProducts.add(product);
                        },
                        () -> log.error("Product Id {} was not found", item.productId())));

    updateInventoryAndNotify(updateProducts, event);
  }

  private void updateInventoryAndNotify(List<ProductEntity> products, PaymentDeclinedEvent event) {
    repository.saveAll(products);
    rabbitTemplate.convertAndSend(
        INVENTORY_RESTORED_QUEUE,
        InventoryEventMapper.toInventoryRestoredEvent(event.getOrderId()));
  }
}

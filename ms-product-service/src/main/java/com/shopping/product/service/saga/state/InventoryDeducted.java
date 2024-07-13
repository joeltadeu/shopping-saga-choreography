package com.shopping.product.service.saga.state;

import static com.shopping.lib.commons.util.ShoppingQueue.INVENTORY_DECLINED_QUEUE;
import static com.shopping.lib.commons.util.ShoppingQueue.INVENTORY_DEDUCTED_QUEUE;

import com.shopping.lib.commons.model.OrderItem;
import com.shopping.lib.commons.model.OrderItemChecked;
import com.shopping.lib.commons.model.enums.OrderItemCheckedStatus;
import com.shopping.lib.commons.model.events.OrderCreatedEvent;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.repository.ProductRepository;
import com.shopping.product.service.mapper.InventoryEventMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InventoryDeducted {

  private final ProductRepository repository;
  private final RabbitTemplate rabbitTemplate;

  public void process(OrderCreatedEvent event) {
    List<OrderItemChecked> checkedItems = new ArrayList<>();
    List<ProductEntity> productsToUpdate = new ArrayList<>();

    event.getItems().forEach(item -> processOrderItem(item, checkedItems, productsToUpdate));

    if (allItemsAvailable(checkedItems)) {
      updateInventoryAndNotify(productsToUpdate, event);
    } else {
      notifyInventoryDeclined(event.getOrderId());
    }
  }

  private void processOrderItem(
      OrderItem item, List<OrderItemChecked> checkedItems, List<ProductEntity> productsToUpdate) {
    repository
        .findById(item.productId())
        .ifPresentOrElse(
            product -> handleProductAvailability(item, product, checkedItems, productsToUpdate),
            () ->
                checkedItems.add(
                    new OrderItemChecked(item.productId(), OrderItemCheckedStatus.NOT_AVAILABLE)));
  }

  private void handleProductAvailability(
      OrderItem item,
      ProductEntity product,
      List<OrderItemChecked> checkedItems,
      List<ProductEntity> productsToUpdate) {
    int remainingQuantity = product.getQuantity() - item.quantity();
    if (remainingQuantity >= 0) {
      product.setQuantity(remainingQuantity);
      productsToUpdate.add(product);
      checkedItems.add(new OrderItemChecked(product.getId(), OrderItemCheckedStatus.AVAILABLE));
    } else {
      checkedItems.add(new OrderItemChecked(product.getId(), OrderItemCheckedStatus.NOT_AVAILABLE));
    }
  }

  private boolean allItemsAvailable(List<OrderItemChecked> checkedItems) {
    return checkedItems.stream()
        .allMatch(item -> item.status() == OrderItemCheckedStatus.AVAILABLE);
  }

  private void updateInventoryAndNotify(List<ProductEntity> products, OrderCreatedEvent event) {
    repository.saveAll(products);
    rabbitTemplate.convertAndSend(
        INVENTORY_DEDUCTED_QUEUE, InventoryEventMapper.toInventoryDeductedEvent(event));
  }

  private void notifyInventoryDeclined(Long orderId) {
    rabbitTemplate.convertAndSend(
        INVENTORY_DECLINED_QUEUE, InventoryEventMapper.toInventoryDeclinedEvent(orderId));
  }
}

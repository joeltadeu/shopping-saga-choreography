package com.shopping.order.dto;

import com.shopping.lib.commons.model.OrderItem;
import com.shopping.lib.commons.model.Payment;
import com.shopping.order.entity.OrderEntity;
import com.shopping.order.entity.OrderItemEntity;
import java.math.BigDecimal;
import java.util.List;

public record Order(Long customerId, List<OrderItem> items, Payment payment) {

    public static OrderEntity toEntity(Long customerId, Order order) {
        var orderEntity = new OrderEntity();
        orderEntity.setCustomerId(customerId);
        orderEntity.setTotal(getTotal(order.items()));
        orderEntity.setItems(getOrderItems(order.items()));
        return orderEntity;
    }

    private static BigDecimal getTotal(List<OrderItem> items) {
        return items.stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItemEntity> getOrderItems(List<OrderItem> items) {
        return items.stream().map(i -> new OrderItemEntity(i.productId(), i.quantity(), i.price())).toList();
    }
}

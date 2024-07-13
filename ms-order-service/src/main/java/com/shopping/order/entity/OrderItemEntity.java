package com.shopping.order.entity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
public class OrderItemEntity {
  private Long productId;
  private Integer quantity;

  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal price;

  public OrderItemEntity(Long productId, Integer quantity, BigDecimal price) {
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
  }
}

package com.shopping.order.entity;

import com.shopping.lib.commons.model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "tb_orders")
@Data
public class OrderEntity {

  @Transient public static final String SEQUENCE_NAME = "orders_sequence";

  @MongoId private Long id;

  @Indexed(name = "customer_id_index")
  private Long customerId;

  @Field(targetType = FieldType.DATE_TIME)
  private LocalDateTime createdAt;

  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal total;

  private List<OrderItemEntity> items;

  private OrderStatus status;
}

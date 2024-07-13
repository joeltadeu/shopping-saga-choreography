package com.shopping.payment.entity;

import com.shopping.lib.commons.model.enums.PaymentStatus;
import com.shopping.payment.dto.PaymentCharge;
import com.shopping.payment.dto.PaymentToken;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "tb_payments")
@Data
public class PaymentOrderEntity {
  @Transient public static final String SEQUENCE_NAME = "payments_sequence";

  @MongoId private Long id;

  @Indexed(name = "order_id_index")
  private Long orderId;

  @Field(targetType = FieldType.DATE_TIME)
  private LocalDateTime createdAt;

  @Field(targetType = FieldType.DATE_TIME)
  private LocalDateTime updatedAt;

  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal amount;

  private String chargeId;

  private String token;

  private String failedReason;

  private PaymentStatus status;

  public static PaymentOrderEntity toEntity(
      Long orderId, BigDecimal amount, PaymentToken paymentToken) {
    var entity = new PaymentOrderEntity();
    entity.setOrderId(orderId);
    entity.setAmount(amount);
    entity.setStatus(paymentToken.getStatus());
    entity.setToken(paymentToken.getToken());
    entity.setFailedReason(paymentToken.getFailedReason());
    return entity;
  }

  public static PaymentOrderEntity toEntity(
      Long orderId, BigDecimal amount, PaymentCharge paymentCharge, PaymentToken paymentToken) {
    var entity = toEntity(orderId, amount, paymentToken);
    entity.setChargeId(paymentCharge.getChargeId());
    entity.setStatus(paymentCharge.getStatus());
    Optional.ofNullable(paymentCharge.getFailedReason()).ifPresent(entity::setFailedReason);
    return entity;
  }
}

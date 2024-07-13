package com.shopping.lib.commons.model.events;

import com.shopping.lib.commons.model.OrderItem;
import com.shopping.lib.commons.model.Payment;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class PaymentDeductedEvent extends Event {
  private Long orderId;
  private BigDecimal total;
  private Payment payment;
  private List<OrderItem> items;
}

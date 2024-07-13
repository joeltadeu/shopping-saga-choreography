package com.shopping.lib.commons.model.events;

import com.shopping.lib.commons.model.OrderItem;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class PaymentDeclinedEvent extends Event {
  List<OrderItem> items;
}

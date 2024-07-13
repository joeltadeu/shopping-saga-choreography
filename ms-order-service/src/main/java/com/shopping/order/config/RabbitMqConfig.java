package com.shopping.order.config;

import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.shopping.lib.commons.util.ShoppingQueue.*;

@Configuration
public class RabbitMqConfig {

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Declarable inventoryRestoredQueue() {
    return new Queue(INVENTORY_RESTORED_QUEUE);
  }

  @Bean
  public Declarable inventoryDeclinedQueue() {
    return new Queue(INVENTORY_DECLINED_QUEUE);
  }

  @Bean
  public Declarable orderCompletedQueue() {
    return new Queue(ORDER_COMPLETED_QUEUE);
  }

  @Bean
  public Declarable inventoryDeductedQueue() {
    return new Queue(INVENTORY_DEDUCTED_QUEUE);
  }
}

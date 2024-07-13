package com.shopping.order.controller;

import com.shopping.order.dto.Order;
import com.shopping.order.dto.OrderResponse;
import com.shopping.order.service.OrderService;
import com.shopping.order.service.mapper.OrderEventMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers/{customerId}/orders")
public class OrderController {
  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<OrderResponse>> findAll(
      @PathVariable("customerId") Long customerId,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

    var pageResponse = service.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
    return new ResponseEntity<>(OrderResponse.toModel(pageResponse), HttpStatus.OK);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(
      @PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId) {
    var order = service.findById(customerId, orderId);
    return ResponseEntity.ok(OrderResponse.toModel(order));
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(
      @PathVariable("customerId") Long customerId, @RequestBody Order order) {
    var entity = Order.toEntity(customerId, order);
    service.save(entity, order.payment());
    return new ResponseEntity<>(OrderResponse.toModel(entity), HttpStatus.CREATED);
  }
}

package com.shopping.product.controller;

import com.shopping.product.dto.Product;
import com.shopping.product.dto.ProductResponse;
import com.shopping.product.dto.ProductSearchCriteria;
import com.shopping.product.entity.ProductEntity;
import com.shopping.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
    var product = service.findById(id);
    return ResponseEntity.ok(ProductResponse.toModel(product));
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponse>> findAll(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      ProductSearchCriteria criteria) {
    Page<ProductEntity> productPage = service.findAll(PageRequest.of(page, size), criteria);
    return new ResponseEntity<>(ProductResponse.toModel(productPage), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ProductResponse> insert(@RequestBody Product product) {
    var saved = service.insert(Product.toEntity(product));
    return new ResponseEntity<>(ProductResponse.toModel(saved), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(@RequestBody Product product, @PathVariable Long id) {
    service.update(Product.toEntity(product), id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}

package com.shopping.payment.repository;

import java.util.Optional;

import com.shopping.payment.entity.PaymentOrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository extends MongoRepository<PaymentOrderEntity, Long> {
    Optional<PaymentOrderEntity> findByOrderId(Long orderId);
}

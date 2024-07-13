package com.shopping.payment.service.broker;

import com.shopping.lib.commons.model.Payment;
import com.shopping.payment.dto.PaymentCharge;
import com.shopping.payment.dto.PaymentToken;
import java.math.BigDecimal;

public interface BrokerService {
    PaymentToken token(Long orderId, Payment payment);
    PaymentCharge charge(Long orderId, BigDecimal amount, String token);
}

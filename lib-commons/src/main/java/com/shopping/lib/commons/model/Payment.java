package com.shopping.lib.commons.model;

import java.math.BigDecimal;

public record Payment(
    String cardNumber,
    String expirationMonth,
    String expirationYear,
    String cvc,
    BigDecimal amount) {}

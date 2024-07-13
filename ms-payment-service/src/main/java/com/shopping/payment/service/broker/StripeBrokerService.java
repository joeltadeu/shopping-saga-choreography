package com.shopping.payment.service.broker;

import com.shopping.lib.commons.model.Payment;
import com.shopping.lib.commons.model.enums.PaymentStatus;
import com.shopping.payment.dto.PaymentCharge;
import com.shopping.payment.dto.PaymentToken;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeBrokerService implements BrokerService {
  private static String STRIPE_PUBLIC_KEY;
  private static String STRIPE_SECRET_KEY;

  @Override
  public PaymentToken token(Long orderId, Payment payment) {
    var paymentToken = new PaymentToken(orderId);

    try {
      Token token = getToken(payment);
      paymentToken.setToken(token.getId());
      paymentToken.setStatus(PaymentStatus.TOKEN_CREATED);

    } catch (StripeException e) {
      paymentToken.setStatus(PaymentStatus.TOKEN_FAILED);
      paymentToken.setFailedReason(e.getMessage());
    }

    return paymentToken;
  }

  @Override
  public PaymentCharge charge(Long orderId, BigDecimal amount, String token) {
    var paymentCharge = new PaymentCharge();

    Optional.ofNullable(token)
        .ifPresentOrElse(
            paymentToken -> {
              try {
                Charge charge = getCharge(orderId, amount, paymentToken);
                paymentCharge.setChargeId(charge.getId());
                paymentCharge.setStatus(PaymentStatus.SUCCEEDED);
              } catch (StripeException e) {
                paymentCharge.setStatus(PaymentStatus.REJECTED);
                paymentCharge.setFailedReason(e.getMessage());
              }
            },
            () -> paymentCharge.setStatus(PaymentStatus.TOKEN_FAILED));

    return paymentCharge;
  }

  private static Charge getCharge(Long orderId, BigDecimal amount, String token)
      throws StripeException {
    RequestOptions requestOptions = RequestOptions.builder().setApiKey(STRIPE_SECRET_KEY).build();
    Map<String, Object> chargeParams = createChargeParams(orderId, amount, token);
    return Charge.create(chargeParams, requestOptions);
  }

  private static Token getToken(Payment payment) throws StripeException {
    RequestOptions requestOptions = RequestOptions.builder().setApiKey(STRIPE_PUBLIC_KEY).build();
    Map<String, Object> params = createTokenParams(payment);
    return Token.create(params, requestOptions);
  }

  @Value("${api.stripe.public.key}")
  public void setStripePublicKey(String stripePublicKey) {
    STRIPE_PUBLIC_KEY = stripePublicKey;
  }

  @Value("${api.stripe.secret.key}")
  public void setStripeSecretKey(String stripeSecretKey) {
    STRIPE_SECRET_KEY = stripeSecretKey;
  }

  private static Map<String, Object> createChargeParams(
      Long orderId, BigDecimal amount, String token) {
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", (amount.multiply(BigDecimal.valueOf(100))).intValue());
    chargeParams.put("currency", "USD");
    chargeParams.put("description", "Payment for order id " + orderId);
    chargeParams.put("source", token);
    Map<String, Object> metaData = new HashMap<>();
    metaData.put("id", orderId);
    chargeParams.put("metadata", metaData);
    return chargeParams;
  }

  private static Map<String, Object> createTokenParams(Payment payment) {
    Map<String, Object> card = new HashMap<>();
    card.put("number", payment.cardNumber());
    card.put("exp_month", Integer.parseInt(payment.expirationMonth()));
    card.put("exp_year", Integer.parseInt(payment.expirationYear()));
    card.put("cvc", payment.cvc());
    Map<String, Object> params = new HashMap<>();
    params.put("card", card);
    return params;
  }
}

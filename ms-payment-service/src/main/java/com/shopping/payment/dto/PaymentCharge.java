package com.shopping.payment.dto;

import com.shopping.lib.commons.model.enums.PaymentStatus;

public class PaymentCharge {
  private String chargeId;
  private PaymentStatus status;
  private String failedReason;

  public PaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }

  public String getChargeId() {
    return chargeId;
  }

  public void setChargeId(String chargeId) {
    this.chargeId = chargeId;
  }

  public String getFailedReason() {
    return failedReason;
  }

  public void setFailedReason(String failedReason) {
    this.failedReason = failedReason;
  }
}

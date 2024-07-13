package com.shopping.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shopping")
public class PaymentApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaymentApplication.class, args);
  }
}

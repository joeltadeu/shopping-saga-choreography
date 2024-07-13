package com.shopping.payment.config;

import org.springframework.context.annotation.Configuration;

@Configuration(value = "api.stripe")
public class StripeProperties {
    private String publicKey;
    private String secretKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

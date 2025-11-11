package com.webhook.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CommonUtils {

    public String generateSignatureSecret() {
        // Generate a random 32-byte secret and encode it in Base64
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

}

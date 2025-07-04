package com.futureim.voicecommerce.gateway.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class GenerateJwtKey {
    public static void main(String[] args) {
        // Generate a secure key for HS256 algorithm
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // Encode it as Base64
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Generated JWT Key (Base64):");
        System.out.println(secretString);
    }
}

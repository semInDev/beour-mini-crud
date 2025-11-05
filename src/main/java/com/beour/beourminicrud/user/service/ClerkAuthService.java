package com.beour.beourminicrud.user.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
public class ClerkAuthService {

    private static final String CLERK_JWKS_URL = "https://api.clerk.dev/v1/jwks"; // Clerk 공개키 URL

    /**
     * Clerk JWT 검증
     */
    public Claims verifyClerkToken(String token) {
        try {
            // Clerk의 JWKS(JSON Web Key Set) 가져오기
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> jwks = restTemplate.getForObject(CLERK_JWKS_URL, Map.class);

            // Clerk 서버가 발급한 JWT의 서명 검증 (간소화 예시)
            // 실제 구현에서는 kid값에 따라 올바른 public key 선택
            PublicKey publicKey = ClerkPublicKeyUtils.getFirstKey(jwks);

            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Clerk JWT 검증 실패: {}", e.getMessage());
            return null;
        }
    }
}

package io.spring.planner.authorization.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.spring.planner.authorization.jwt.exception.AuthorizationException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractTokenProvider<T> {

    private final String TOKEN_PREFIX = "bearer ";
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
    private final SecretKey secretKey;
    private final Long expiredMinutes;
    private final ObjectMapper objectMapper;

    public AbstractTokenProvider(String secretKey, Long expiredMinutes, ObjectMapper objectMapper) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.objectMapper = objectMapper;
        this.expiredMinutes = expiredMinutes;
    }

    public String generate(T payload) {

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("data", payload);

        Long expiredTime = 1000 * 60L * expiredMinutes;

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);

        // 토큰 Builder
        return Jwts.builder()
            .signWith(secretKey)
            .header()
            .add("typ", "JWT")
            .add("alg", algorithm.getValue())
            .and()
            .claims(payloads)
            .subject("token")
            .expiration(ext)
            .compact();
    }

    public T validate(String token) throws AuthorizationException {
        try {
            if (token == null || token == "") {
                throw new JwtException("token is invalid");
            }

            Claims payload = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(replacePrefix(token))
                .getPayload();

            return objectMapper.convertValue(payload.get("data"), getPayloadClassType());
        } catch (JwtException exception) {
            if (exception instanceof SignatureException) {
                throw new AuthorizationException("JWT Signature does not matched", "JWT_SIGNATURE_INVALID");
            } else if (exception instanceof ExpiredJwtException) {
                throw new AuthorizationException("JWT is expired", "JWT_EXPIRED");
            }

            log.warn(exception.getMessage());
            throw new AuthorizationException("token is invalid", "JWT_EXCEPTION");
        }
    }

    private String replacePrefix(String token) {
        return token.replace(TOKEN_PREFIX, "");
    }

    abstract protected Class<T> getPayloadClassType();
}

package io.spring.planner.authorization.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.planner.authorization.AuthorizationPayload;
import io.spring.planner.authorization.AuthorizationToken;

public class TokenManager {
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    public TokenManager(String accessTokenSecretKey, String refreshTokenSecretKey, Long accessTokenExpiredMinutes, Long refreshTokenExpiredMinutes, ObjectMapper objectMapper) {
        this.accessTokenProvider = new AccessTokenProvider(accessTokenSecretKey, accessTokenExpiredMinutes, objectMapper);
        this.refreshTokenProvider = new RefreshTokenProvider(refreshTokenSecretKey, refreshTokenExpiredMinutes, objectMapper);
    }

    public AuthorizationToken generateToken(AuthorizationPayload payload) {
        return new AuthorizationToken(
            payload,
            accessTokenProvider.generate(payload),
            refreshTokenProvider.generate(payload)
        );
    }

    public AuthorizationPayload validateAccessToken(String token) {
        return accessTokenProvider.validate(token);
    }

    public AuthorizationPayload validateRefreshToken(String token) {
        return refreshTokenProvider.validate(token);
    }

}
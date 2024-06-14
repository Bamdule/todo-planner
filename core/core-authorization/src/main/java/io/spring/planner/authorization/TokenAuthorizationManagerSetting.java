package io.spring.planner.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;

public record TokenAuthorizationManagerSetting(
    String accessTokenSecretKey,
    String refreshTokenSecretKey,
    Long accessTokenExpiredMinutes,
    Long refreshTokenExpiredMinutes,
    ObjectMapper objectMapper) {
}

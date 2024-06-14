package io.spring.planner.authorization;

public record AuthorizationToken(AuthorizationPayload payload, String accessToken, String refreshToken) {
}

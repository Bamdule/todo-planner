package io.spring.planner.authorization;


import io.spring.planner.authorization.jwt.exception.AuthorizationException;

public interface AuthorizationManager {
    AuthorizationToken generateAuthorization(AuthorizationPayload request);

    AuthorizationPayload validateAccessToken(String accessToken) throws AuthorizationException;

    AuthorizationToken validateRefreshToken(String refreshToken) throws AuthorizationException;
}

package io.spring.planner.authorization.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.planner.authorization.AuthorizationPayload;

public class AccessTokenProvider extends AbstractTokenProvider<AuthorizationPayload> {
    public AccessTokenProvider(String secretKey, Long expiredMinutes, ObjectMapper objectMapper) {
        super(secretKey, expiredMinutes, objectMapper);
    }

    @Override
    protected Class<AuthorizationPayload> getPayloadClassType() {
        return AuthorizationPayload.class;
    }
}

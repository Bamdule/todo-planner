package io.spring.planner.presentation.config.authorization.interceptor;

import io.spring.planner.authorization.AuthorizationManager;
import io.spring.planner.authorization.AuthorizationMetaData;
import io.spring.planner.authorization.AuthorizationPayload;
import io.spring.planner.authorization.jwt.exception.AuthorizationException;
import io.spring.planner.presentation.config.authorization.AuthorizationHolder;
import io.spring.planner.presentation.config.authorization.AuthorizationInfo;
import io.spring.planner.presentation.config.exception.AuthorizationExceptionCode;
import io.spring.planner.presentation.config.exception.TokenAuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationHandlerInterceptor implements HandlerInterceptor {

    private final AuthorizationManager authorizationManager;
    private final AuthorizationHolder authorizationHolder;

    public AuthorizationHandlerInterceptor(AuthorizationManager authorizationManager, AuthorizationHolder authorizationHolder) {
        this.authorizationManager = authorizationManager;
        this.authorizationHolder = authorizationHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader(AuthorizationMetaData.ACCESS_TOKEN.name());

        if (accessToken == null || accessToken == "") {
            throw new TokenAuthorizationException(AuthorizationExceptionCode.ACCESS_TOKEN_REQUIRED);
        }

        try {
            AuthorizationPayload payload = authorizationManager.validateAccessToken(accessToken);
            setAuthentication(payload);
        } catch (AuthorizationException exception) {
            throw new TokenAuthorizationException(exception.getMessage(), exception.getCode(), HttpStatus.UNAUTHORIZED.value());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void setAuthentication(AuthorizationPayload payload) {
        authorizationHolder.setMemberInfo(new AuthorizationInfo(
            payload.id(),
            payload.email()
        ));
    }
}

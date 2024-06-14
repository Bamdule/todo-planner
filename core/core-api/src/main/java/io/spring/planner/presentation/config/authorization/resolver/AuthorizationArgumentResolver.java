package io.spring.planner.presentation.config.authorization.resolver;

import io.spring.planner.presentation.config.authorization.Authorization;
import io.spring.planner.presentation.config.authorization.AuthorizationHolder;
import io.spring.planner.presentation.config.authorization.AuthorizationInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthorizationArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthorizationHolder authorizationHolder;

    public AuthorizationArgumentResolver(AuthorizationHolder authorizationHolder) {
        this.authorizationHolder = authorizationHolder;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthorizationInfo.class) &&
            parameter.hasParameterAnnotation(Authorization.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return authorizationHolder.getMemberInfo();
    }
}

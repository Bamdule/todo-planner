package io.spring.planner.presentation.config;

import io.spring.planner.authorization.AuthorizationManager;
import io.spring.planner.presentation.config.authorization.AuthorizationHolder;
import io.spring.planner.presentation.config.authorization.interceptor.AuthorizationHandlerInterceptor;
import io.spring.planner.presentation.config.authorization.resolver.AuthorizationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final static List<String> whiteList = Arrays.asList(
        "/api/v1/member-login/**",
        "/favicon.ico",
        "/error",
        "/api/v1/redis-test/**",
        "/api/v1/authorization/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );
    private final AuthorizationManager authorizationManager;

    private final AuthorizationHolder authorizationHolder;

    private final List<String> excludePathPatterns = Arrays.asList();

    public WebConfig(AuthorizationManager authorizationManager, AuthorizationHolder authorizationHolder) {
        this.authorizationManager = authorizationManager;
        this.authorizationHolder = authorizationHolder;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationHandlerInterceptor(authorizationManager, authorizationHolder))
            .excludePathPatterns(whiteList);

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthorizationArgumentResolver(authorizationHolder));
    }
}

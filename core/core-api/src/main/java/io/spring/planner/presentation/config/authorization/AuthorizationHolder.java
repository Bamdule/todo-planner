package io.spring.planner.presentation.config.authorization;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class AuthorizationHolder {
    private AuthorizationInfo authorizationInfo;

    public void setMemberInfo(AuthorizationInfo authorizationInfo) {
        this.authorizationInfo = authorizationInfo;
    }

    public AuthorizationInfo getMemberInfo() {
        return authorizationInfo;
    }
}

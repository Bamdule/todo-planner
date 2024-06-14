package io.spring.planner.presentation.controller.login;

public record MemberLoginResponse(
    String accessToken,
    String refreshToken
) {
}

package io.spring.planner.presentation.controller.login;

public record MemberLoginRefreshResponse(
    String accessToken,
    String refreshToke
) {
}

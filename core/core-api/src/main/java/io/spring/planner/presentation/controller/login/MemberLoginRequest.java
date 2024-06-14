package io.spring.planner.presentation.controller.login;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
    @NotBlank(message = "이메일은 필수 값입니다.")
    String email,
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    String password
) {
}

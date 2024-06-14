package io.spring.planner.presentation.controller.plan;

import jakarta.validation.constraints.NotBlank;

public record PlanRegistrationRequest(@NotBlank(message = "할 일 제목은 필수 값입니다.") String title) {
}

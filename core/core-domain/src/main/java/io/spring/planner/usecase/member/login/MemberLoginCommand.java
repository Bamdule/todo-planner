package io.spring.planner.usecase.member.login;

public record MemberLoginCommand(
    String email,
    String password
) {

}

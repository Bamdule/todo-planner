package io.spring.planner.domain.member;

import io.spring.planner.domain.common.Email;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Member {
    private Long id;

    private String nickname;

    private Email email;

    private MemberPassword password;

    private MemberRegistrationState registrationState;

    private Member(Long id, String nickname, Email email, MemberPassword password, MemberRegistrationState registrationState) {
        Objects.requireNonNull(password, "password must be not null");
        Objects.requireNonNull(nickname, "nickname must be not null");
        Objects.requireNonNull(email, "email must be not null");
        Objects.requireNonNull(registrationState, "nickname registrationState be not null");

        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.registrationState = registrationState;
    }

    public static Member register(String nickname, Email email, MemberPassword password) {
        return new Member(
            null,
            nickname,
            email,
            password,
            MemberRegistrationState.REGISTRATION
        );
    }

    public static Member convert(Long id, String nickname, Email email, MemberPassword password, MemberRegistrationState registrationState) {
        Objects.requireNonNull(id, "id must be not null");
        return new Member(
            id,
            nickname,
            email,
            password,
            registrationState
        );
    }

    public boolean isDeleted() {
        return this.registrationState == MemberRegistrationState.DELETION;
    }
}

package io.spring.planner.usecase.member.login;

import io.spring.planner.domain.common.Email;
import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.Member;
import io.spring.planner.domain.member.MemberPassword;
import io.spring.planner.domain.member.MemberRepository;
import io.spring.planner.domain.member.exception.MemberDeletedException;
import io.spring.planner.domain.member.exception.MemberLoginFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberLoginUseCase {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberLoginResult login(MemberLoginCommand command) {
        Email email = new Email(command.email());

        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberLoginFailureException(ExceptionCode.MEMBER_LOGIN_FAILURE));

        validatePassword(command.password(), member.getPassword());

        if(member.isDeleted()) {
            throw new MemberDeletedException(ExceptionCode.MEMBER_DELETED);
        }

        return new MemberLoginResult(
            member.getId(),
            member.getNickname(),
            member.getEmail().getValue()
        );
    }

    private void validatePassword(String password, MemberPassword memberPassword) {
        if (!passwordEncoder.matches(password, memberPassword.getEncodedPassword())) {
            throw new MemberLoginFailureException(ExceptionCode.MEMBER_LOGIN_FAILURE);
        }
    }
}

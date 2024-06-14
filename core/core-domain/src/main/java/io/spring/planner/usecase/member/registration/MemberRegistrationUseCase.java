package io.spring.planner.usecase.member.registration;

import io.spring.planner.domain.common.Email;
import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.Member;
import io.spring.planner.domain.member.exception.MemberEmailAlreadyExistedException;
import io.spring.planner.domain.member.MemberPassword;
import io.spring.planner.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class MemberRegistrationUseCase {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberRegistrationResult register(MemberRegistrationCommand command) {
        Email email = new Email(command.email());

        this.validateEmailDuplicated(email);

        MemberPassword memberPassword = createMemberPassword(command);

        Member member = Member.register(
            command.nickname(),
            email,
            memberPassword
        );

        Member registeredMember = memberRepository.save(member);

        return new MemberRegistrationResult(
            registeredMember.getId(),
            registeredMember.getNickname(),
            registeredMember.getEmail().getValue()
        );
    }

    private void validateEmailDuplicated(Email email) {
        memberRepository.findByEmail(email)
            .ifPresent(member -> {
                throw new MemberEmailAlreadyExistedException(ExceptionCode.MEMBER_EMAIL_ALREADY_EXISTED);
            });
    }

    private MemberPassword createMemberPassword(MemberRegistrationCommand command) {
        return new MemberPassword(command.password(), passwordEncoder.encode(command.password()));
    }
}

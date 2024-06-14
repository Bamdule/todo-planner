package io.spring.planner.usecase.member.find;

import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.Member;
import io.spring.planner.domain.member.MemberRepository;
import io.spring.planner.domain.member.exception.MemberDeletedException;
import io.spring.planner.domain.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberFindUseCase {

    private final MemberRepository memberRepository;

    public MemberFindResult findById(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(ExceptionCode.MEMBER_NOT_FOUND));

        if (member.isDeleted()) {
            throw new MemberDeletedException(ExceptionCode.MEMBER_DELETED);
        }

        return new MemberFindResult(
            member.getId(),
            member.getNickname(),
            member.getEmail().getValue(),
            member.getRegistrationState()
        );
    }
}

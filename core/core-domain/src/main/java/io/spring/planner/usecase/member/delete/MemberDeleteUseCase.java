package io.spring.planner.usecase.member.delete;

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
public class MemberDeleteUseCase {

    private final MemberRepository memberRepository;

    @Transactional
    public void deleteById(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(ExceptionCode.MEMBER_NOT_FOUND));

        if (member.isDeleted()) {
            throw new MemberDeletedException(ExceptionCode.MEMBER_DELETED);
        }

        memberRepository.updateDeletionState(member);
    }
}

package io.spring.planner.jpa.entity.member;

import io.spring.planner.domain.common.Email;
import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.Member;
import io.spring.planner.domain.member.MemberRepository;
import io.spring.planner.domain.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberCoreRepository implements MemberRepository {


    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.create(member)).toModel();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id).map(MemberEntity::toModel);
    }

    @Override
    public Optional<Member> findByEmail(Email email) {
        return memberJpaRepository.findByEmail(email.getValue()).map(MemberEntity::toModel);
    }

    @Override
    public void updateDeletionState(Member member) {
        memberJpaRepository.findById(member.getId())
            .orElseThrow(() -> new MemberNotFoundException(ExceptionCode.MEMBER_NOT_FOUND))
            .delete();

    }


}

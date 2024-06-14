package io.spring.planner.domain.member;

import io.spring.planner.domain.common.Email;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(Email email);

    void updateDeletionState(Member member);
}

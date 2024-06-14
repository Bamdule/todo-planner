package io.spring.planner.domain.member;

import io.spring.planner.domain.common.Email;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 회원_도메인_생성() {

        Member member = Member.convert(1L, "tester", new Email("tester@example.com"), new MemberPassword("ENCODED"), MemberRegistrationState.REGISTRATION);
        Assertions.assertThat(member).isNotNull();
    }

}
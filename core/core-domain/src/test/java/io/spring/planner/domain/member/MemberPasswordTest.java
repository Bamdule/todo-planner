package io.spring.planner.domain.member;

import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.exception.MemberPasswordPatternInvalidException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("비밀번호 생성 테스트")
class MemberPasswordTest {
    final PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Test
    void 비밀번호_최초_생성_테스트() {
        final String password = "sdasdasdasdasdsad!A1";
        MemberPassword memberPassword = new MemberPassword(password, pwEncoder.encode(password));
        Assertions.assertThat(memberPassword).isNotNull();
    }

    @DisplayName("비밀번호 정규식 실패 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"!A112345678910111111111", "sdasdasdasdasdsad!A111", "abcdefghijklmnop", "1sdasdasdasdasdsad!A1"})
    void 비밀번호_최초_생성_시_비밀번호_정규식_검증실패_예외발생(String password) {
        Assertions.assertThatThrownBy(() -> new MemberPassword(password, pwEncoder.encode(password)))
            .isInstanceOf(MemberPasswordPatternInvalidException.class)
            .hasMessage(ExceptionCode.MEMBER_PASSWORD_PATTERN_INVALID.getMessage());
    }

    @Test
    void 암호화된_비밀번호_생성_테스트() {
        final String password = "sdasdasdasdasdsad!A1";
        MemberPassword memberPassword = new MemberPassword(pwEncoder.encode(password));
        Assertions.assertThat(memberPassword.getEncodedPassword()).isNotBlank();
        Assertions.assertThat(memberPassword.getPassword()).isNull();
    }
}
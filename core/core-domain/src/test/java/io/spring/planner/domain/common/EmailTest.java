package io.spring.planner.domain.common;

import io.spring.planner.domain.common.exception.EmailPatternInvalidException;
import io.spring.planner.domain.common.exception.ExceptionCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void 이메일_생성_성공() {
        String testEmail = "tester@example.com";

        Email email = new Email(testEmail);

        Assertions.assertThat(email).isNotNull();
    }

    @Test
    void 이메일_형식_검증_실패_예외() {
        String testEmail = "testerexample.com";

        Assertions.assertThatThrownBy(() -> new Email(testEmail))
            .isInstanceOf(EmailPatternInvalidException.class)
            .hasMessage(ExceptionCode.EMAIL_PATTERN_INVALID.getMessage());
    }

}
package io.spring.planner.domain.common;

import io.spring.planner.domain.common.exception.EmailPatternInvalidException;
import io.spring.planner.domain.common.exception.ExceptionCode;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Email {
    private String value;
    private static final String EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    private static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final String EMAIL_REGEX_PATTERN_INVALID_MESSAGE = "이메일 형식이 잘못되었습니다. (ex: 'tester@example.com')";


    public Email(String email) {
        Objects.requireNonNull(email, "email must not be null");

        if (!EMAIL_REGEX_PATTERN.matcher(email).find()) {
            throw new EmailPatternInvalidException(ExceptionCode.EMAIL_PATTERN_INVALID);

        }

        this.value = email;
    }
}

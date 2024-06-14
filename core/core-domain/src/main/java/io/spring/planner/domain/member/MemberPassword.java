package io.spring.planner.domain.member;

import io.spring.planner.domain.common.exception.ExceptionCode;
import io.spring.planner.domain.member.exception.MemberPasswordPatternInvalidException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class MemberPassword {
    private String password;
    private String encodedPassword;

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,20}$";
    private static final Pattern PASSWORD_REGEX_PATTERN = Pattern.compile(PASSWORD_REGEX);
    public static final String PASSWORD_REGEX_PATTERN_INVALID_MESSAGE = "비밀번호는 숫자, 문자, 특수문자(~!@#$%^&*)를 포함해서 최소 8자리에서 20자 이내로 입력해야합니다";

    public MemberPassword(String password, String encodedPassword) {
        Objects.requireNonNull(password, "password must be not null");

        if (!PASSWORD_REGEX_PATTERN.matcher(password).find()) {
            throw new MemberPasswordPatternInvalidException(ExceptionCode.MEMBER_PASSWORD_PATTERN_INVALID);
        }

        this.password = password;
        this.encodedPassword = encodedPassword;
    }

    public MemberPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}

package io.spring.planner.domain.common.exception;

import io.spring.planner.domain.common.Email;
import io.spring.planner.domain.member.MemberPassword;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    EMAIL_PATTERN_INVALID(Email.EMAIL_REGEX_PATTERN_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    MEMBER_PASSWORD_PATTERN_INVALID(MemberPassword.PASSWORD_REGEX_PATTERN_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    MEMBER_NOT_FOUND("존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND),
    MEMBER_DELETED("탈퇴한 회원입니다.", HttpStatus.BAD_REQUEST),
    MEMBER_LOGIN_FAILURE("이메일 혹은 비밀번호가 잘못되었습니다", HttpStatus.UNAUTHORIZED),

    MEMBER_EMAIL_ALREADY_EXISTED("이미 존재하는 회원 이메일입니다", HttpStatus.BAD_REQUEST),
    PLAN_PENDING_STATE_UPDATE_FAILURE("상태가 진행중인 경우에만 대기 상태로 변경할 수 있습니다.", HttpStatus.BAD_REQUEST),
    PLAN_NOT_FOUND("존재하지 않는 할일 정보 입니다", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

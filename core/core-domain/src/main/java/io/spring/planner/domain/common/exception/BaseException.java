package io.spring.planner.domain.common.exception;

public class BaseException extends RuntimeException {
    private final String message;
    private final String code;
    private final int httpStatus;

    public BaseException(String message, String code, int httpStatus) {
        super(message);
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}

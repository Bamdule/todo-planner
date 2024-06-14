package io.spring.planner.presentation.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResponse {

    private final LocalDateTime timestamp;
    private final String message;
    private final String code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<InvalidValueInfo> invalidValueInfos;


    public ExceptionResponse(String message, String code, List<InvalidValueInfo> invalidValueInfos) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.code = code;
        this.invalidValueInfos = invalidValueInfos;
    }

    public ExceptionResponse(String message, String code) {
        this(message, code, null);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public List<InvalidValueInfo> getInvalidValueInfos() {
        return invalidValueInfos;
    }

    public record InvalidValueInfo(
        String field,
        Object value,
        String message
    ) {
    }
}

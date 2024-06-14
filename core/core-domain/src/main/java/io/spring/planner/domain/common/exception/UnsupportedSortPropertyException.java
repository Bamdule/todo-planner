package io.spring.planner.domain.common.exception;

import org.springframework.http.HttpStatus;

public class UnsupportedSortPropertyException extends DomainException {
    public UnsupportedSortPropertyException(String property) {
        super(String.format("[%s]는 지원하지 않는 정렬 속성입니다", property), "UNSUPPORTED_SORT_PROPERTY", HttpStatus.BAD_REQUEST.value());
    }
}

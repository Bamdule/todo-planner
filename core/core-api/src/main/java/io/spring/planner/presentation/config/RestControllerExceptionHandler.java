package io.spring.planner.presentation.config;

import io.spring.planner.presentation.config.exception.ExceptionResponse;
import io.spring.planner.presentation.config.exception.TokenAuthorizationException;
import io.spring.planner.domain.common.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ExceptionResponse response = new ExceptionResponse(
            "요청 메시지가 잘못 되었습니다.",
            "HTTP_MESSAGE_NOT_READABLE"
        );

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<ExceptionResponse.InvalidValueInfo> invalidValueInfos = exception.getBindingResult().getFieldErrors()
            .stream()
            .map(error -> new ExceptionResponse.InvalidValueInfo(
                error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()
            ))
            .toList();

        ExceptionResponse response = new ExceptionResponse(
            "잘못된 파라미터를 입력했습니다.",
            "PARAMETERS_INVALID",
            invalidValueInfos
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionResponse> handleDomainException(DomainException exception) {
        ExceptionResponse response = new ExceptionResponse(
            exception.getMessage(),
            exception.getCode()
        );

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

    @ExceptionHandler(TokenAuthorizationException.class)
    public ResponseEntity<ExceptionResponse> handleTokenAuthorizationException(TokenAuthorizationException exception) {
        ExceptionResponse response = new ExceptionResponse(
            exception.getMessage(),
            exception.getCode()
        );

        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(exception.getMessage(), "MISSING_PARAMETER"));
    }


}

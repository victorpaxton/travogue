package com.hcmut.travogue.exception;

import com.hcmut.travogue.model.dto.Response.ErrorDTO;
import com.hcmut.travogue.model.dto.Response.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Stream;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllException(Exception ex, WebRequest webRequest) {
        return new ErrorDTO("500", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseModel<Object> handlerRequestException(NotFoundException ex) {
        return ResponseModel.builder()
                .isSuccess(false)
                .data(null)
                .errors(Stream.of(new ErrorDTO("404", ex.getMessage())).toList())
                .build();
    }

    @ExceptionHandler({
            BadRequestException.class,
            BindException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handlerRequestException(Exception ex) {
        return new ErrorDTO("400", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ErrorDTO("400", ex.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO unauthorizedException(Exception ex) {
        return new ErrorDTO("401", ex.getMessage());
    }

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO forbiddenException(Exception ex) {
        return new ErrorDTO("403", ex.getMessage());
    }
}

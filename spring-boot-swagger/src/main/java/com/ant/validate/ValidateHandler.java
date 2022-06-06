package com.ant.validate;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ValidateHandler {

    /**
     * MethodArgumentNotValidException 异常处理
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public String methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        return "fds";
    }

    /**
     * 字段未传异常处理
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public String exceptionValidHandler(Exception e) {
        return "fds";
    }

    @ExceptionHandler(BindException.class)
    public String exceptionBindException(BindException bindException){
        List<FieldError> allErrors = bindException.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : allErrors) {
            sb.append(errorMessage.getField()).append(": ").append(errorMessage.getDefaultMessage()).append(", ");
        }
        System.out.println(sb.toString());
        return "fds";
    }
}

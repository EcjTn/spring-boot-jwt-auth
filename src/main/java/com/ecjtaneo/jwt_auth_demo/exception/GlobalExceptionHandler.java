package com.ecjtaneo.jwt_auth_demo.exception;

import com.ecjtaneo.jwt_auth_demo.dto.response.MessageResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MessageResponse handleConflict(RuntimeException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleNotFound(RuntimeException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleValidationErrors(Exception ex) {
        return new MessageResponse("Request body is missing or invalid");
    }

    @ExceptionHandler({
            ExpiredJwtException.class, MalformedJwtException.class,
            UnsupportedJwtException.class, SecurityException.class,
            SignatureException.class, JwtException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResponse handleInvalidJwt() {
        return new MessageResponse("Invalid or Expired JWT.");
    }

}

package com.epam.esm.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private int exceptionCode;

    public JwtAuthenticationException(String message){
        super(message);
    }

    public JwtAuthenticationException(String message, int exceptionCode){
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public int getExceptionCode(){
        return exceptionCode;
    }
}

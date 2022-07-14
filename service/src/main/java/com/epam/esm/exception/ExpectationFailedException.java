package com.epam.esm.exception;

public class ExpectationFailedException extends Exception {
    private final String exceptionCode;

    public ExpectationFailedException(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getCode() {
        return exceptionCode;
    }
}
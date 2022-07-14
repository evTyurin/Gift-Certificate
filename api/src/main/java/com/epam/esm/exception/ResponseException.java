package com.epam.esm.exception;

/**
 * The type of custom exception used for response
 */
public class ResponseException {

    private final String errorMessage;
    private final String errorCode;

    public ResponseException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
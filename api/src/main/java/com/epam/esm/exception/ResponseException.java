package com.epam.esm.exception;

/**
 * The type of custom exception used for response
 */
public class ResponseException {

    private final String errorMessage;
    private final int errorCode;

    public ResponseException(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
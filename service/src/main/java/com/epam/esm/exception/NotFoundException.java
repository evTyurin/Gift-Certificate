package com.epam.esm.exception;

public class NotFoundException extends Exception {

    private final int id;
    private final String exceptionCode;

    public NotFoundException(int id, String exceptionCode) {
        this.id = id;
        this.exceptionCode = exceptionCode;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return exceptionCode;
    }
}
package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type of custom exception used for response
 */
@AllArgsConstructor
@Getter
public class ResponseException {

    private final String errorMessage;
    private final int errorCode;
}
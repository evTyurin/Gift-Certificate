package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundLoginException extends Exception{

    private final String login;
    private final int exceptionCode;
}

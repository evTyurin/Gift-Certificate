package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityExistException extends Exception {

    private final int id;
    private final int exceptionCode;
}
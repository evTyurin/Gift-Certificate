package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageNumberException extends Exception {

    private final int pagesAmount;
    private final int exceptionCode;
}
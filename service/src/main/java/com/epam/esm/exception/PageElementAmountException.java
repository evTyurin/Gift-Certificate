package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageElementAmountException extends Exception {

    private final int exceptionCode;
}
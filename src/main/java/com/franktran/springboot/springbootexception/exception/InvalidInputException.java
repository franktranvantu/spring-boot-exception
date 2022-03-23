package com.franktran.springboot.springbootexception.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvalidInputException extends RuntimeException {
    private final String message;
}

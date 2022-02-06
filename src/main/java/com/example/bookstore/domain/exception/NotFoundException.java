package com.example.bookstore.domain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String exception) {
        super(exception);
    }
}

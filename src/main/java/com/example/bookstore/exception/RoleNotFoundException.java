package com.example.bookstore.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String exception) {
        super(exception);
    }
}

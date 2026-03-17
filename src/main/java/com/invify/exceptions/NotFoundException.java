package com.invify.exceptions;

public class NotFoundException extends BadRequestException {
    public NotFoundException(String message) {
        super(message);
    }
}

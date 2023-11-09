package com.quick.recording.auth.service.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> clazz, Object object) {
        super(String.format("%s not found by value = %s", clazz.getSimpleName(), object));
    }

    public NotFoundException(String message) {
        super(message);
    }
}

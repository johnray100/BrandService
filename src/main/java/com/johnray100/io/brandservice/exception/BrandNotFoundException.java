package com.johnray100.io.brandservice.exception;

public class BrandNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BrandNotFoundException(String message) {
        super(message);
    }
}

package com.example.luxmed.exception;

import java.io.Serial;

public class CompanyNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CompanyNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

package com.ssctech.ssctech_test_task.constants.errors;

public enum ValidationError {
    
    NULL_INPUT("Input must not be null"),
    EMPTY_INPUT("Input must not be empty"),
    INVALID_FORMAT("Input format is invalid");
    
    private final String message;
    
    ValidationError(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}

package com.ssctech.ssctech_test_task.constants.config;

public enum EncodingConfig {
    
    INITIAL_CAPACITY_MULTIPLIER(2),
    MAX_CHAR_COUNT(Integer.MAX_VALUE);
    
    private final int value;
    
    EncodingConfig(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

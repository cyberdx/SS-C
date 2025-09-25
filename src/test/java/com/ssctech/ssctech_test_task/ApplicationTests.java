package com.ssctech.ssctech_test_task;

import com.ssctech.ssctech_test_task.services.LengthEncoding;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Application Integration Tests")
class ApplicationTests {

    @Autowired
    private LengthEncoding lengthEncodingService;

    @Test
    @DisplayName("Spring Boot application should start successfully")
    void contextLoads() {
        assertNotNull(lengthEncodingService, "LengthEncoding service should be available");
    }

    @Test
    @DisplayName("Application should have proper service configuration")
    void applicationShouldHaveProperServiceConfiguration() {
        assertNotNull(lengthEncodingService, "LengthEncoding service should be injected");
        
        String result = lengthEncodingService.encode("test");
        assertNotNull(result, "Service should return a result");
        assertEquals("t1e1s1t1", result, "Service should encode correctly");
    }

    @Test
    @DisplayName("Application should handle null inputs gracefully")
    void applicationShouldHandleNullInputsGracefully() {
        assertThrows(IllegalArgumentException.class, 
            () -> lengthEncodingService.encode((String) null),
            "Service should throw IllegalArgumentException for null string input");
        
        assertThrows(IllegalArgumentException.class, 
            () -> lengthEncodingService.encode((char[]) null),
            "Service should throw IllegalArgumentException for null char array input");
    }

    @ParameterizedTest
    @DisplayName("Application should be ready for production use")
    @ValueSource(strings = {
        "Hello World",
        "AAA",
        "AAABBBCCC",
        "123456789",
        "!@#$%^&*()"
    })
    void applicationShouldBeReadyForProductionUse(String testCase) {
        String result = lengthEncodingService.encode(testCase);
        assertNotNull(result, "Result should not be null for: " + testCase);
        assertFalse(result.isEmpty(), "Result should not be empty for: " + testCase);
    }
}

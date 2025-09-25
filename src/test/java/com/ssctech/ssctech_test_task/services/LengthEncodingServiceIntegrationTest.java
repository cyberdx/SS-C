package com.ssctech.ssctech_test_task.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.main.banner-mode=off",
    "logging.level.org.springframework=WARN"
})
@DisplayName("LengthEncoding Service Integration Tests")
class LengthEncodingServiceIntegrationTest {

    @Autowired
    private LengthEncoding lengthEncodingService;

    @Test
    @DisplayName("Spring context should load and inject LengthEncoding service")
    void springContextShouldLoadAndInjectLengthEncodingService() {
        assertNotNull(lengthEncodingService, "LengthEncoding service should be injected");
        assertInstanceOf(LengthEncodingService.class, lengthEncodingService,
            "Injected service should be instance of LengthEncodingService");
    }

    @Test
    @DisplayName("Injected service should work correctly with char array")
    void injectedServiceShouldWorkCorrectlyWithCharArray() {
        char[] input = {'a', 'a', 'a', 'b', 'b', 'c'};

        String result = lengthEncodingService.encode(input);

        assertEquals("a3b2c1", result);
    }

    @Test
    @DisplayName("Injected service should work correctly with string")
    void injectedServiceShouldWorkCorrectlyWithString() {
        String input = "aaabbbccc";

        String result = lengthEncodingService.encode(input);

        assertEquals("a3b3c3", result);
    }

    @Test
    @DisplayName("Injected service should handle null inputs correctly")
    void injectedServiceShouldHandleNullInputsCorrectly() {
        String nullString = null;
        char[] nullCharArray = null;

        assertThrows(IllegalArgumentException.class,
            () -> lengthEncodingService.encode(nullString),
            "String method should throw IllegalArgumentException for null input");
        
        assertThrows(IllegalArgumentException.class,
            () -> lengthEncodingService.encode(nullCharArray),
            "Char array method should throw IllegalArgumentException for null input");
    }

    @Test
    @DisplayName("Injected service should handle empty inputs correctly")
    void injectedServiceShouldHandleEmptyInputsCorrectly() {
        String emptyString = "";
        char[] emptyCharArray = {};

        String stringResult = lengthEncodingService.encode(emptyString);
        String charArrayResult = lengthEncodingService.encode(emptyCharArray);

        assertEquals("", stringResult);
        assertEquals("", charArrayResult);
        assertEquals(stringResult, charArrayResult,
            "Both methods should produce consistent results for empty input");
    }

    @ParameterizedTest
    @DisplayName("Injected service should work with complex real-world scenarios")
    @ValueSource(strings = {
        "user@example.com",
        "file123.txt", 
        "API_KEY_12345",
        "data.json",
        "config.yaml",
        "README.md",
        "package.json",
        "Dockerfile"
    })
    void injectedServiceShouldWorkWithComplexRealWorldScenarios(String input) {
        String result = lengthEncodingService.encode(input);

        assertNotNull(result, "Result should not be null for: " + input);
        assertFalse(result.isEmpty(), "Result should not be empty for: " + input);
        
        String charArrayResult = lengthEncodingService.encode(input.toCharArray());
        assertEquals(result, charArrayResult,
            "Both methods should produce consistent results for: " + input);
        
        assertTrue(result.matches("([^0-9]\\d+)+"), 
            "Result should match RLE pattern (character followed by count) for: " + input);
    }

    @ParameterizedTest
    @DisplayName("Service should handle various file extensions correctly")
    @ValueSource(strings = {
        ".java", ".class", ".jar", ".war", ".ear", ".xml", ".properties", ".yml", ".yaml"
    })
    void serviceShouldHandleVariousFileExtensionsCorrectly(String extension) {
        String testInput = "TestFile" + extension;

        String result = lengthEncodingService.encode(testInput);

        assertNotNull(result, "Result should not be null for: " + testInput);
        assertFalse(result.isEmpty(), "Result should not be empty for: " + testInput);
        
        assertTrue(result.contains(extension.substring(0, 1)), 
            "Result should contain encoded extension for: " + testInput);
    }

    @Test
    @DisplayName("Service should be thread-safe for concurrent access")
    void serviceShouldBeThreadSafeForConcurrentAccess() throws InterruptedException {
        String testString = "concurrent_test_string";
        int threadCount = 10;
        int iterationsPerThread = 100;
        Thread[] threads = new Thread[threadCount];
        boolean[] results = new boolean[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < iterationsPerThread; j++) {
                        String result = lengthEncodingService.encode(testString);
                        assertNotNull(result);
                        assertFalse(result.isEmpty());
                    }
                    results[threadIndex] = true;
                } catch (Exception e) {
                    results[threadIndex] = false;
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 0; i < threadCount; i++) {
            assertTrue(results[i], "Thread " + i + " should complete successfully");
        }
    }
}

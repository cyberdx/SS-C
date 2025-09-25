package com.ssctech.ssctech_test_task.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LengthEncodingService Tests")
class LengthEncodingServiceTest {

    private LengthEncodingService lengthEncoding;

    @BeforeEach
    void setUp() {
        lengthEncoding = new LengthEncodingService();
    }

    @Nested
    @DisplayName("Char Array Encoding Tests")
    class CharArrayEncodingTests {

        @Test
        @DisplayName("Should encode simple repeated characters")
        void shouldEncodeSimpleRepeatedCharacters() {
            char[] input = {'a', 'a', 'a', 'b', 'b', 'c'};

            String result = lengthEncoding.encode(input);

            assertEquals("a3b2c1", result);
        }

        @Test
        @DisplayName("Should encode single character")
        void shouldEncodeSingleCharacter() {
            char[] input = {'x'};

            String result = lengthEncoding.encode(input);

            assertEquals("x1", result);
        }

        @Test
        @DisplayName("Should encode all same characters")
        void shouldEncodeAllSameCharacters() {
            char[] input = {'a', 'a', 'a', 'a', 'a'};

            String result = lengthEncoding.encode(input);

            assertEquals("a5", result);
        }

        @Test
        @DisplayName("Should encode mixed characters with no repetitions")
        void shouldEncodeMixedCharactersWithNoRepetitions() {
            char[] input = {'a', 'b', 'c', 'd', 'e'};

            String result = lengthEncoding.encode(input);

            assertEquals("a1b1c1d1e1", result);
        }

        @Test
        @DisplayName("Should handle empty char array")
        void shouldHandleEmptyCharArray() {
            char[] input = {};

            String result = lengthEncoding.encode(input);

            assertEquals("", result);
        }

        @Test
        @DisplayName("Should throw exception for null char array")
        void shouldThrowExceptionForNullCharArray() {
            char[] input = null;
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> lengthEncoding.encode(input));
            assertEquals("Input must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Should encode with special characters")
        void shouldEncodeWithSpecialCharacters() {
            char[] input = {'!', '!', '?', '?', '?', '@'};

            String result = lengthEncoding.encode(input);

            assertEquals("!2?3@1", result);
        }

        @Test
        @DisplayName("Should encode with digits")
        void shouldEncodeWithDigits() {
            char[] input = {'1', '1', '1', '2', '3', '3'};

            String result = lengthEncoding.encode(input);

            assertEquals("132132", result);
        }

        @Test
        @DisplayName("Should handle large counts")
        void shouldHandleLargeCounts() {
            char[] input = new char[1000];
            for (int i = 0; i < 1000; i++) {
                input[i] = 'a';
            }

            String result = lengthEncoding.encode(input);

            assertEquals("a1000", result);
        }
    }

    @Nested
    @DisplayName("String Encoding Tests")
    class StringEncodingTests {

        @Test
        @DisplayName("Should encode simple string")
        void shouldEncodeSimpleString() {
            String input = "aaabbbccc";

            String result = lengthEncoding.encode(input);

            assertEquals("a3b3c3", result);
        }

        @Test
        @DisplayName("Should encode single character string")
        void shouldEncodeSingleCharacterString() {
            String input = "z";

            String result = lengthEncoding.encode(input);

            assertEquals("z1", result);
        }

        @Test
        @DisplayName("Should encode empty string")
        void shouldEncodeEmptyString() {
            String input = "";

            String result = lengthEncoding.encode(input);

            assertEquals("", result);
        }

        @Test
        @DisplayName("Should throw exception for null string")
        void shouldThrowExceptionForNullString() {
            String input = null;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> lengthEncoding.encode(input));
            assertEquals("Input must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Should encode string with spaces")
        void shouldEncodeStringWithSpaces() {
            String input = "aa bb cc";

            String result = lengthEncoding.encode(input);

            assertEquals("a2 1b2 1c2", result);
        }

        @Test
        @DisplayName("Should encode string with mixed case")
        void shouldEncodeStringWithMixedCase() {
            String input = "AaAaaBbB";

            String result = lengthEncoding.encode(input);

            assertEquals("A1a1A1a2B1b1B1", result);
        }

        @Test
        @DisplayName("Should encode complex string pattern")
        void shouldEncodeComplexStringPattern() {
            String input = "aaaabbbccccccdd";

            String result = lengthEncoding.encode(input);

            assertEquals("a4b3c6d2", result);
        }

        @Test
        @DisplayName("Should encode string with punctuation")
        void shouldEncodeStringWithPunctuation() {
            String input = "!!!???";

            String result = lengthEncoding.encode(input);

            assertEquals("!3?3", result);
        }

        @Test
        @DisplayName("Should encode very long string")
        void shouldEncodeVeryLongString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                sb.append('x');
            }
            String input = sb.toString();

            String result = lengthEncoding.encode(input);

            assertEquals("x100", result);
        }
    }

    @Nested
    @DisplayName("Edge Cases and Boundary Tests")
    class EdgeCasesAndBoundaryTests {

        @Test
        @DisplayName("Should handle alternating characters")
        void shouldHandleAlternatingCharacters() {
            char[] input = {'a', 'b', 'a', 'b', 'a', 'b'};

            String result = lengthEncoding.encode(input);

            assertEquals("a1b1a1b1a1b1", result);
        }

        @Test
        @DisplayName("Should handle pattern with increasing repetitions")
        void shouldHandlePatternWithIncreasingRepetitions() {
            char[] input = {'a', 'b', 'b', 'c', 'c', 'c', 'd', 'd', 'd', 'd'};

            String result = lengthEncoding.encode(input);

            assertEquals("a1b2c3d4", result);
        }

        @Test
        @DisplayName("Should handle unicode characters")
        void shouldHandleUnicodeCharacters() {
            char[] input = {'ñ', 'ñ', 'ñ', 'ü', 'ü'};

            String result = lengthEncoding.encode(input);

            assertEquals("ñ3ü2", result);
        }

        @Test
        @DisplayName("Should handle newline and tab characters")
        void shouldHandleNewlineAndTabCharacters() {
            char[] input = {'\n', '\n', '\t', '\t', '\t'};

            String result = lengthEncoding.encode(input);

            assertEquals("\n2\t3", result);
        }

        @Test
        @DisplayName("Should handle string with newlines")
        void shouldHandleStringWithNewlines() {
            String input = "aa\n\nbb\t\t\tcc";

            String result = lengthEncoding.encode(input);

            assertEquals("a2\n2b2\t3c2", result);
        }
    }

    @Nested
    @DisplayName("Performance and Stress Tests")
    class PerformanceAndStressTests {

        @Test
        @DisplayName("Should handle large input efficiently")
        void shouldHandleLargeInputEfficiently() {
            char[] input = new char[10000];
            for (int i = 0; i < input.length; i++) {
                input[i] = (char) ('a' + (i % 26));
            }

            long startTime = System.currentTimeMillis();
            String result = lengthEncoding.encode(input);
            long endTime = System.currentTimeMillis();

            assertNotNull(result);
            assertFalse(result.isEmpty());
            // Verify it completes in reasonable time (less than 1 second)
            assertTrue(endTime - startTime < 1000, "Encoding should complete in less than 1 second");
        }

        @Test
        @DisplayName("Should handle maximum integer count boundary")
        void shouldHandleMaximumIntegerCountBoundary() {
            
            char[] input = new char[100000];
            for (int i = 0; i < input.length; i++) {
                input[i] = 'x';
            }

            String result = lengthEncoding.encode(input);

            assertEquals("x100000", result);
        }
    }
}

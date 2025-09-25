package com.ssctech.ssctech_test_task.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LengthEncoding Interface Contract Tests")
class LengthEncodingTest {

    private final LengthEncoding lengthEncoding = new LengthEncodingService();

    @Nested
    @DisplayName("Interface Contract Validation")
    class InterfaceContractValidation {

        @Test
        @DisplayName("Both encode methods should produce same result for equivalent input")
        void bothEncodeMethodsShouldProduceSameResultForEquivalentInput() {
            String testString = "aaabbbccc";
            char[] testCharArray = testString.toCharArray();

            String stringResult = lengthEncoding.encode(testString);
            String charArrayResult = lengthEncoding.encode(testCharArray);

            assertEquals(stringResult, charArrayResult, 
                "Both encode methods should produce identical results for equivalent input");
        }

        @Test
        @DisplayName("String encode should handle null consistently with char array encode")
        void stringEncodeShouldHandleNullConsistentlyWithCharArrayEncode() {
            String nullString = null;
            char[] nullCharArray = null;

            IllegalArgumentException stringException = assertThrows(IllegalArgumentException.class,
                    () -> lengthEncoding.encode(nullString));
            IllegalArgumentException charArrayException = assertThrows(IllegalArgumentException.class,
                    () -> lengthEncoding.encode(nullCharArray));

            assertEquals(stringException.getMessage(), charArrayException.getMessage(),
                "Both methods should throw the same exception message for null input");
        }

        @Test
        @DisplayName("String encode should handle empty string consistently with empty char array")
        void stringEncodeShouldHandleEmptyStringConsistentlyWithEmptyCharArray() {
            String emptyString = "";
            char[] emptyCharArray = {};

            String stringResult = lengthEncoding.encode(emptyString);
            String charArrayResult = lengthEncoding.encode(emptyCharArray);

            assertEquals(stringResult, charArrayResult,
                "Both methods should produce identical results for empty input");
        }

        @Test
        @DisplayName("Both methods should be deterministic")
        void bothMethodsShouldBeDeterministic() {
            String testString = "hello world";
            char[] testCharArray = testString.toCharArray();

            String stringResult1 = lengthEncoding.encode(testString);
            String stringResult2 = lengthEncoding.encode(testString);
            String charArrayResult1 = lengthEncoding.encode(testCharArray);
            String charArrayResult2 = lengthEncoding.encode(testCharArray);

            assertEquals(stringResult1, stringResult2,
                "String encode should be deterministic");
            assertEquals(charArrayResult1, charArrayResult2,
                "Char array encode should be deterministic");
            assertEquals(stringResult1, charArrayResult1,
                "Both methods should produce consistent results");
        }

        @Test
        @DisplayName("Results should not be null for valid non-empty input")
        void resultsShouldNotBeNullForValidNonEmptyInput() {
            String testString = "test";
            char[] testCharArray = {'t', 'e', 's', 't'};

            String stringResult = lengthEncoding.encode(testString);
            String charArrayResult = lengthEncoding.encode(testCharArray);

            assertNotNull(stringResult, "String encode result should not be null");
            assertNotNull(charArrayResult, "Char array encode result should not be null");
        }

        @Test
        @DisplayName("Results should be non-empty strings for non-empty input")
        void resultsShouldBeNonEmptyStringsForNonEmptyInput() {
            String testString = "a";
            char[] testCharArray = {'a'};

            String stringResult = lengthEncoding.encode(testString);
            String charArrayResult = lengthEncoding.encode(testCharArray);

            assertFalse(stringResult.isEmpty(), "String encode result should not be empty");
            assertFalse(charArrayResult.isEmpty(), "Char array encode result should not be empty");
        }

        @Test
        @DisplayName("Encoding should preserve character order")
        void encodingShouldPreserveCharacterOrder() {
            String testString = "aaabbbcccaaabbbccc";
            char[] testCharArray = testString.toCharArray();

            String stringResult = lengthEncoding.encode(testString);
            String charArrayResult = lengthEncoding.encode(testCharArray);

            // Verify that 'a' comes before 'b', 'b' comes before 'c' in the result
            assertTrue(stringResult.indexOf('a') < stringResult.indexOf('b'),
                "Character order should be preserved in string encoding");
            assertTrue(stringResult.indexOf('b') < stringResult.indexOf('c'),
                "Character order should be preserved in string encoding");
            
            assertEquals(stringResult, charArrayResult,
                "Both methods should preserve order consistently");
        }

        @Test
        @DisplayName("Encoding should handle all ASCII printable characters")
        void encodingShouldHandleAllAsciiPrintableCharacters() {
            StringBuilder sb = new StringBuilder();
            for (int i = 32; i <= 126; i++) { // ASCII printable range
                sb.append((char) i);
            }
            String testString = sb.toString();

            String stringResult = lengthEncoding.encode(testString);
            String charArrayResult = lengthEncoding.encode(testString.toCharArray());

            assertNotNull(stringResult);
            assertNotNull(charArrayResult);
            assertEquals(stringResult, charArrayResult,
                "Both methods should handle all ASCII characters consistently");
            
            // Verify result contains counts for each character
            assertTrue(stringResult.length() > testString.length(),
                "Encoded result should be longer than input for unique characters");
        }

        @Test
        @DisplayName("Encoding should be case sensitive")
        void encodingShouldBeCaseSensitive() {
            String testString = "AaAa";
            char[] testCharArray = testString.toCharArray();

            String stringResult = lengthEncoding.encode(testString);
            String charArrayResult = lengthEncoding.encode(testCharArray);

            assertEquals("A1a1A1a1", stringResult,
                "Encoding should be case sensitive");
            assertEquals(stringResult, charArrayResult,
                "Both methods should handle case sensitivity consistently");
        }
    }

    @Nested
    @DisplayName("Cross-Method Consistency Tests")
    class CrossMethodConsistencyTests {

        @ParameterizedTest
        @DisplayName("Complex pattern should be handled consistently")
        @ValueSource(strings = {
            "aaabbbcccddd",
            "abcdefghijklmnopqrstuvwxyz",
            "aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz",
            "1234567890",
            "!@#$%^&*()",
            "   \t\n\r",
            "MixedCase123!@#"
        })
        void complexPatternShouldBeHandledConsistently(String testCase) {
            String stringResult = lengthEncoding.encode(testCase);
            String charArrayResult = lengthEncoding.encode(testCase.toCharArray());

            assertEquals(stringResult, charArrayResult,
                "Both methods should produce identical results for: " + testCase);
        }

        @Test
        @DisplayName("Performance should be consistent between methods")
        void performanceShouldBeConsistentBetweenMethods() {
            String testString = "a".repeat(1000) + "b".repeat(1000) + "c".repeat(1000);

            long stringStartTime = System.nanoTime();
            String stringResult = lengthEncoding.encode(testString);
            long stringEndTime = System.nanoTime();

            long charArrayStartTime = System.nanoTime();
            String charArrayResult = lengthEncoding.encode(testString.toCharArray());
            long charArrayEndTime = System.nanoTime();

            assertEquals(stringResult, charArrayResult,
                "Results should be identical");
            
            // Both methods should complete in reasonable time
            long stringDuration = stringEndTime - stringStartTime;
            long charArrayDuration = charArrayEndTime - charArrayStartTime;
            
            assertTrue(stringDuration < 1_000_000_000, // 1 second in nanoseconds
                "String method should complete in reasonable time");
            assertTrue(charArrayDuration < 1_000_000_000, // 1 second in nanoseconds
                "Char array method should complete in reasonable time");
        }
    }
}

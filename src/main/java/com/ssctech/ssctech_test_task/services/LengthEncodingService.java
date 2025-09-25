package com.ssctech.ssctech_test_task.services;

import com.ssctech.ssctech_test_task.constants.config.EncodingConfig;
import com.ssctech.ssctech_test_task.constants.errors.ValidationError;
import org.springframework.stereotype.Service;

/**
 * Service implementation for Run-Length Encoding (RLE) operations.
 * 
 * <p>Run-Length Encoding is a simple form of data compression where consecutive
 * identical characters are replaced with the character followed by its count.
 * 
 * <p>Examples:
 * <ul>
 *   <li>"AAA" → "A3"</li>
 *   <li>"AAABBBCCC" → "A3B3C3"</li>
 *   <li>"ABCDE" → "A1B1C1D1E1"</li>
 * </ul>
 * 
 * <p>This implementation is thread-safe and handles edge cases such as:
 * <ul>
 *   <li>Null inputs (throws IllegalArgumentException)</li>
 *   <li>Empty inputs (returns empty string)</li>
 *   <li>Large character counts (handles Integer.MAX_VALUE boundary)</li>
 *   <li>Unicode characters and special characters</li>
 * </ul>
 * 
 * @author SSC Tech
 * @since 1.0
 */
@Service
public class LengthEncodingService implements LengthEncoding {

    @Override
    public String encode(char[] input) {
        validateInput(input);
        
        if (input.length == 0) {
            return "";
        }
        
        return performEncoding(input);
    }

    @Override
    public String encode(String input) {
        validateInput(input);
        return encode(input.toCharArray());
    }

    private void validateInput(Object input) {
        if (input == null) {
            throw new IllegalArgumentException(ValidationError.NULL_INPUT.getMessage());
        }
    }

    private String performEncoding(char[] input) {
        StringBuilder result = new StringBuilder(input.length * EncodingConfig.INITIAL_CAPACITY_MULTIPLIER.getValue());
        
        char currentChar = input[0];
        int count = 1;
        
        for (int i = 1; i < input.length; i++) {
            char nextChar = input[i];
            
            if (nextChar == currentChar) {
                if (count == EncodingConfig.MAX_CHAR_COUNT.getValue()) {
                    appendEncodedPair(result, currentChar, count);
                    count = 0;
                }
                count++;
            } else {
                appendEncodedPair(result, currentChar, count);
                currentChar = nextChar;
                count = 1;
            }
        }
        
        appendEncodedPair(result, currentChar, count);
        
        return result.toString();
    }

    private void appendEncodedPair(StringBuilder result, char character, int count) {
        result.append(character).append(count);
    }
}

package utils;

/**
 * Simple unchecked validation exception for defensive programming.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

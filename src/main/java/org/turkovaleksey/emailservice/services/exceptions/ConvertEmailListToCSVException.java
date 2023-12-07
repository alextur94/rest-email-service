package org.turkovaleksey.emailservice.services.exceptions;

/**
 * Exception indicating an error during the conversion of a list of emails to a CSV file.
 * This exception is thrown when there is an issue encountered during the process of
 * converting a list of email objects into a CSV file format. The exception provides
 * details about the nature of the conversion error.
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * try {
 *     // code that may throw ConvertEmailListToCSVException
 * } catch (ConvertEmailListToCSVException ex) {
 *     // handle the exception
 *     logger.error("Error converting email list to CSV: " + ex.getMessage());
 * }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * This exception may include a cause, which represents the underlying exception that
 * led to the conversion failure. Use {@link #getCause()} to retrieve the cause.
 * </p>
 *
 * @see RuntimeException
 */
public class ConvertEmailListToCSVException extends RuntimeException {
    public ConvertEmailListToCSVException(String message) {
        super(message);
    }
}

package org.turkovaleksey.emailservice.services.exceptions;

/**
 * Exception indicating an incorrect period where the start date is later than the end date.
 * This exception is thrown when attempting to work with a period where the specified start date
 * is greater than the specified end date, which violates the expected chronological order.
 * The exception provides information about the nature of the incorrect period.
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * try {
 *     // code that may throw IncorrectPeriodException
 *     validateDates(startDate, endDate);
 * } catch (IncorrectPeriodException ex) {
 *     // handle the exception
 *     logger.error("Invalid period: " + ex.getMessage());
 * }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * This exception may include a cause, which represents the underlying exception that
 * led to the validation failure. Use {@link #getCause()} to retrieve the cause.
 * </p>
 *
 * <p>
 * It is important to validate periods to ensure that start dates are always before or equal
 * to end dates, and this exception helps to identify and handle cases where this condition is not met.
 * </p>
 *
 * @see RuntimeException
 */
public class IncorrectPeriodException extends RuntimeException {
    public IncorrectPeriodException(String message) {
        super(message);
    }
}

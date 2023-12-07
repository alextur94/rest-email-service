package org.turkovaleksey.emailservice.repositories.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Map;

/**
 * An exception indicating a Bad Request in the application.
 * This exception is typically thrown when the client sends a request that
 * cannot be processed by the server due to a client error (e.g., invalid input).
 * The exception provides information about the nature of the bad request.
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * try {
 *     // code that may throw BadRequestException
 * } catch (BadRequestException ex) {
 *     // handle the exception
 *     logger.error("Bad Request: " + "User with same email already exists");
 * }
 * }
 * </pre>
 * </p>
 *
 * @see RuntimeException
 */
public class AppError400 {
    @Schema(description = "Error Class")
    private String errorClass;
    @Schema(description = "List Errors", example = "Email is not correctly")
    private Map<String, String> errors;
    private Date timestamp;

    public AppError400(String errorClass, Map<String, String> errors) {
        this.errorClass = errorClass;
        this.errors = errors;
        this.timestamp = new Date();
    }

    public String getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "AppError{" +
                "errorClass='" + errorClass + '\'' +
                ", errors=" + errors +
                ", timestamp=" + timestamp +
                '}';
    }
}

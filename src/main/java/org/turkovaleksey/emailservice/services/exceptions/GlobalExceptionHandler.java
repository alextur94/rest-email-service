package org.turkovaleksey.emailservice.services.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.turkovaleksey.emailservice.repositories.exceptions.AppError400;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling and processing exceptions across the application.
 * The {@code GlobalExceptionHandler} is responsible for capturing and handling exceptions
 * that occur during the execution of the application. It provides a centralized mechanism
 * to handle various types of exceptions and generate appropriate responses.
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * @ControllerAdvice
 * public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
 *
 *     @ExceptionHandler(Exception.class)
 *     public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
 *         // Custom logic to handle the exception and generate an appropriate response
 *         ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage());
 *         return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
 *     }
 * }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * The {@code GlobalExceptionHandler} typically uses the Spring MVC's {@code @ControllerAdvice}
 * and {@code @ExceptionHandler} annotations to intercept exceptions globally.
 * </p>
 *
 * <p>
 * It extends {@code ResponseEntityExceptionHandler} to inherit common exception handling
 * functionality provided by Spring Boot.
 * </p>
 *
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 * @see org.springframework.web.bind.annotation.ExceptionHandler
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<AppError400> handlerDataAccessException(DataAccessException e) {
        logger.error("DataAccessException - {}", e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        AppError400 error = new AppError400(
                "DataAccessException",
                map
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncorrectPeriodException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError400> handlerIncorrectPeriodException(IncorrectPeriodException e) {
        logger.error("IncorrectPeriodException - {}", e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        AppError400 error = new AppError400(
                "IncorrectPeriodException",
                map
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConvertEmailListToCSVException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AppError400> handlerGetObjectIsNullException(ConvertEmailListToCSVException e) {
        logger.error("ConvertEmailListToCSVException - {}", e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        AppError400 error = new AppError400(
                "ConvertEmailListToCSVException",
                map
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        logger.error("RuntimeException - {}", e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        AppError400 error = new AppError400(
                "RuntimeException",
                map
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleGeneralException(Exception e) {
        logger.error("Exception - {}", e.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        AppError400 error = new AppError400(
                "Exception",
                map
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

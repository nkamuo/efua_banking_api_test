package com.whitespace.bankapi.exception;

import com.whitespace.bankapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catch any RuntimeException and transform it into a friendly error message
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        // You can customize errorCode and message as needed.
        ErrorResponse errorResponse = new ErrorResponse(
//                "RUNTIME_ERROR",
                ex.getClass().getSimpleName(),
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now()
        );
        // Optionally, log the exception details for debugging.
        // logger.error("Runtime exception caught: ", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Catch any AccountNotFound and transform it into a friendly error message
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Catch any AccountNotFound and transform it into a friendly error message
    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(InsufficientFundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Catch any BankingException and transform it into a friendly error message
    @ExceptionHandler(BankingException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(BankingException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now()
        );
        // Optionally, log the exception details for debugging.
        // logger.error("Runtime exception caught: ", ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // handling IllegalArgumentException differently:
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INVALID_ARGUMENT",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

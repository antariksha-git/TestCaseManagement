package ai.zomind.testcasemanagement.exceptionhandler;

import ai.zomind.testcasemanagement.exception.InvalidDataException;
import ai.zomind.testcasemanagement.exception.ResourceNotFoundException;
import ai.zomind.testcasemanagement.exception.errormessage.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        log.error("Filed validation error. Enter valid input");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ApiErrorResponse
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message(errorMessage)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found. Check entered id");
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ApiErrorResponse
                    .builder()
                    .status(HttpStatus.NOT_FOUND.toString())
                    .message(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidDataException(InvalidDataException ex) {
        log.error("Invalid data. Enter the valid input only");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ApiErrorResponse
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}

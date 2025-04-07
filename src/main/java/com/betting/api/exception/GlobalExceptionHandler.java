package com.betting.api.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author kidkat
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(400);
        error.setError("Validation failed");
        error.setMessage(ex.getMessage());

//        Map<String, Object> response = new LinkedHashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", 400);
//        response.put("error", "Validation failed");
//
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> errorIn = new HashMap<>();
            errorIn.put("field", fieldError.getField());
            errorIn.put("message", fieldError.getDefaultMessage());
            errors.add(errorIn);
        }

//        response.put("errors", errors);
        error.setErrors(errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(BadRequestException ex) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(400);
        error.setError("Bad Request");
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourcesFound(ResourceNotFoundException ex) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(404);
        error.setError("Resource not found");
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleParseError(HttpMessageNotReadableException ex) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(400);
        error.setError("Invalid Format");
        error.setMessage("Invalid request format");

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormat) {
            List<JsonMappingException.Reference> path = invalidFormat.getPath();
            if (!path.isEmpty()) {
                String fieldName = path.get(0).getFieldName();
                if ("matchDate".equals(fieldName)) {
                    error.setMessage("Invalid format for field 'match_date'. Expected format: dd/MM/yyyy");
                } else if ("matchTime".equals(fieldName)) {
                    error.setMessage("Invalid format for field 'match_time'. Expected format: HH:mm");
                } else {
                    error.setMessage("Invalid format for field '" + fieldName + "'");
                }
            }
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(404);
        error.setError("Not found");
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleOther(Exception ex) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(500);
        error.setError("Internal Server Error");
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

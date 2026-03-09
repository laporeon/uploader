package com.laporeon.uploader.exceptions;

import com.laporeon.uploader.dto.response.ErrorResponseDTO;
import com.laporeon.uploader.exceptions.custom.FileNotFoundException;
import com.laporeon.uploader.exceptions.custom.InvalidFileNameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFileNameException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidFileNameException(InvalidFileNameException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileNotFoundException(FileNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.CONTENT_TOO_LARGE.value(),
                "MAX_UPLOAD_SIZE_EXCEEDED_ERROR",
                "File size exceeded the upload limit"
        );

        return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        log.error("An unexpected error occurred | exception={} | message={}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex);

        ErrorResponseDTO error = new ErrorResponseDTO(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred"
        );

        return ResponseEntity.internalServerError().body(error);
    }

}

package com.example.gymAp.exceptionTest;

import com.example.gymAp.exception.GlobalExceptionHandler;
import com.example.gymAp.exception.InvalidCredentialsException;
import com.example.gymAp.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    @Test
    void handleInvalidCredentialsException() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        InvalidCredentialsException ex = new InvalidCredentialsException("Invalid credentials");

        ResponseEntity<Object> response = exceptionHandler.handleInvalidCredentialsException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void handleUserNotFoundException() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        UserNotFoundException ex = new UserNotFoundException("User not found");

        ResponseEntity<String> response = exceptionHandler.handleUserNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void handleGenericException() {
        // Arrange
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        Exception ex = mock(Exception.class);

        // Act
        ResponseEntity<String> response = exceptionHandler.handleGenericException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody());
    }
}
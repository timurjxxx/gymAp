package com.example.gymApTest.exceptionTest;

import com.example.gymAp.exception.GlobalExceptionHandler;
import com.example.gymAp.exception.InvalidCredentialsException;
import com.example.gymAp.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

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
    public void testHandleInvalidCredentialsException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        InvalidCredentialsException exception = new InvalidCredentialsException("Invalid credentials");

        ResponseEntity<Object> response = handler.handleInvalidCredentialsException(exception);

        assertEquals("Invalid credentials", response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testHandleBadCredentialsException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        BadCredentialsException exception = new BadCredentialsException("Bad credentials");

        ResponseEntity<String> response = handler.handleBadCredentialsException(exception);

        assertEquals("Bad credentials", response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testHandleGenericException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<String> response = handler.handleGenericException(exception);

        assertEquals("User not found", response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


}
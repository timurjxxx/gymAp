package com.example.gymApTest.exceptionTest;

import com.example.gymAp.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidCredentialsExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String errorMessage = "Invalid credentials";

        InvalidCredentialsException exception = new InvalidCredentialsException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testConstructorWithoutMessage() {
        InvalidCredentialsException exception = new InvalidCredentialsException(null);

        assertEquals(null, exception.getMessage());
    }
}
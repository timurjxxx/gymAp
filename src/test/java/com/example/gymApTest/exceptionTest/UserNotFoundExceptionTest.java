package com.example.gymApTest.exceptionTest;


import com.example.gymAp.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNotFoundExceptionTest {

    @Test
    public void testUserNotFoundException() {
        // Define a message for the exception
        String message = "User not found";

        // Create an instance of UserNotFoundException
        UserNotFoundException exception = new UserNotFoundException(message);

        // Verify that the exception message matches the provided message
        assertEquals(message, exception.getMessage());
    }
}

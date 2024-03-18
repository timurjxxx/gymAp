package com.example.gymApTest.dtoTest;

import com.example.gymAp.dto.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginRequestTest {

    @Test
    public void testLoginRequest() {
        // Create a LoginRequest instance
        String username = "exampleUser";
        String password = "examplePassword";
        LoginRequest request = new LoginRequest(username, password);

        // Verify the getters
        assertEquals(username, request.getUsername());
        assertEquals(password, request.getPassword());
    }

    @Test
    public void testLoginRequestEqualsAndHashCode() {
        // Create LoginRequest instances with same and different values
        LoginRequest request1 = new LoginRequest("user", "password");
        LoginRequest request2 = new LoginRequest("user", "password");
        LoginRequest request3 = new LoginRequest("differentUser", "differentPassword");

        // Verify equals method
        assertEquals(request1, request2); // Two LoginRequest instances with same values should be equal
        assertNotEquals(request1, request3); // Two LoginRequest instances with different values should not be equal

        // Verify hashCode method
        assertEquals(request1.hashCode(), request2.hashCode()); // HashCode of equal LoginRequest instances should be same
        assertNotEquals(request1.hashCode(), request3.hashCode()); // HashCode of different LoginRequest instances should not be same
    }

    @Test
    public void testLoginRequestToString() {
        // Create a LoginRequest instance
        String username = "exampleUser";
        String password = "examplePassword";
        LoginRequest request = new LoginRequest(username, password);

        // Verify toString method
        assertEquals("LoginRequest(username=" + username + ", password=" + password + ")", request.toString());
    }
}

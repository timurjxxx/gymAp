package com.example.gymAp.controllerTest;

import com.example.gymAp.controller.AuthenticationController;
import com.example.gymAp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnOkStatus_WhenAuthenticationSucceeds() {
        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "sampleUser");
        credentials.put("password", "samplePassword");

        doNothing().when(userService).authenticated("sampleUser", "samplePassword");

        ResponseEntity<?> response = authenticationController.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testChangeLogin() {
        // Arrange
        Map<String, String> cred = new HashMap<>();
        cred.put("username", "oldUsername");
        cred.put("password", "oldPassword");
        cred.put("newPassword", "newPassword");


        // Act
        ResponseEntity<?> responseEntity = authenticationController.changeLogin(cred);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(userService, times(1)).authenticated("oldUsername", "oldPassword");
        verify(userService, times(1)).changePassword("oldUsername", "newPassword");
    }
}
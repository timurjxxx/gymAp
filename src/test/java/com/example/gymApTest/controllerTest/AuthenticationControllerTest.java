package com.example.gymApTest.controllerTest;

import com.example.gymAp.controller.AuthenticationController;
import com.example.gymAp.controller.TrainerController;
import com.example.gymAp.dto.ChangeLoginRequest;
import com.example.gymAp.dto.JwtResponse;
import com.example.gymAp.dto.LoginRequest;
import com.example.gymAp.model.Trainee;
import com.example.gymAp.model.Trainer;
import com.example.gymAp.model.TrainingType;
import com.example.gymAp.model.User;
import com.example.gymAp.security.JWTProvider;
import com.example.gymAp.service.AuthService;
import com.example.gymAp.service.TrainerService;
import com.example.gymAp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {
    @Mock
    private AuthService service;

    @Mock
    private JWTProvider provider;

    @InjectMocks
    private AuthenticationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin() {
        LoginRequest request = new LoginRequest("username", "password");
        String token = "mocked_token";
        JwtResponse response1 = new JwtResponse(token);
        when(service.login(request)).thenReturn(response1);

        ResponseEntity<?> response = controller.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, ((JwtResponse) Objects.requireNonNull(response.getBody())).getToken());
        verify(service, times(1)).login(request);
    }


    @Test
    public void testCreateTrainee() {
        Trainee trainee = new Trainee();
        when(service.createTrainee(trainee)).thenReturn("Trainee created successfully");

        ResponseEntity<String> response = controller.createTrainee(trainee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Trainee created successfully", response.getBody());
        verify(service, times(1)).createTrainee(trainee);
    }

    @Test
    public void testCreateTrainer() {
        Trainer trainer = new Trainer();
        when(service.createTrainer(trainer)).thenReturn("Trainer created successfully");

        ResponseEntity<String> response = controller.createTrainer(trainer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Trainer created successfully", response.getBody());
        verify(service, times(1)).createTrainer(trainer);
    }




}

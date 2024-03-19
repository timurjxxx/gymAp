package com.example.gymApTest.serviceTest;

import com.example.gymAp.dto.ChangeLoginRequest;
import com.example.gymAp.dto.JwtResponse;
import com.example.gymAp.dto.LoginRequest;
import com.example.gymAp.model.*;
import com.example.gymAp.security.JWTProvider;
import com.example.gymAp.service.AuthService;
import com.example.gymAp.service.LoginAttemptService;
import com.example.gymAp.service.TraineeService;
import com.example.gymAp.service.TrainerService;
import com.example.gymAp.service.UserService;
import com.example.gymApTest.modelTest.TrainingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private JWTProvider provider;

    @Mock
    private LoginAttemptService loginAttemptService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        // Mocking
        LoginRequest request = new LoginRequest("username", "password");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("username", "password", Collections.emptyList());
        when(userService.loadUserByUsername("username")).thenReturn(userDetails);
        when(encoder.matches("password", userDetails.getPassword())).thenReturn(true);
        when(provider.generateToken(userDetails)).thenReturn("token");

        // Test
        JwtResponse response = authService.login(request);

        // Verify
        assertNotNull(response);
        assertEquals("token", response.getToken());
        verify(loginAttemptService, times(1)).loginSucceeded("username");
    }



    @Test
    void testCreateTrainee() {
        // Arrange
        Trainee trainee = mock(Trainee.class);
        User user = mock(User.class);
        when(trainee.getUser()).thenReturn(user);
        when(traineeService.createTrainee(any(), any())).thenReturn(trainee);

        // Act
        String result = authService.createTrainee(trainee);

        // Assert
        assertEquals("Username :null Password :null", result);
        verify(traineeService).createTrainee(trainee, user);
    }

    @Test
    void testCreateTrainer() {
        // Arrange
        Trainer trainer = mock(Trainer.class);
        User user = mock(User.class);
        when(trainer.getUser()).thenReturn(user);
        when(trainer.getSpecialization()).thenReturn(mock(TrainingType.class));
        when(trainerService.createTrainer(any(), any(), any())).thenReturn(trainer);

        // Act
        String result = authService.createTrainer(trainer);

        // Assert
        assertEquals("Username :null Password :null", result);
        verify(trainerService).createTrainer(trainer, user, trainer.getSpecialization().getTrainingTypeName());
    }
}

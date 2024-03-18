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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogin_Successful() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn("password");
        when(userService.loadUserByUsername("username")).thenReturn(userDetails);
        when(provider.generateToken(any())).thenReturn("token");

        // Act
        JwtResponse jwtResponse = authService.login(loginRequest);

        // Assert
        assertEquals("token", jwtResponse.getToken());
        verify(loginAttemptService).loginSucceeded("username");
    }

    @Test
    void testLogin_BadCredentials() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn("wrong_password");
        when(userService.loadUserByUsername("username")).thenReturn(userDetails);

        // Act & Assert
        try {
            authService.login(loginRequest);
        } catch (BadCredentialsException e) {
            assertEquals("Invalid username or password", e.getMessage());
        }
        verify(loginAttemptService).loginFailed("username");
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        when(userService.loadUserByUsername("username")).thenThrow(new UsernameNotFoundException("User not found"));

        // Act & Assert
        try {
            authService.login(loginRequest);
        } catch (BadCredentialsException e) {
            assertEquals("Invalid username or password", e.getMessage());
        }
        verify(loginAttemptService).loginFailed("username");
    }

    @Test
    void testChangeLogin_Successful() {
        // Arrange
        ChangeLoginRequest changeLoginRequest = new ChangeLoginRequest();
        changeLoginRequest.setUsername("username");
        changeLoginRequest.setOldPassword("old_password");
        changeLoginRequest.setNewPassword("new_password");

        User user = mock(User.class);
        when(user.getPassword()).thenReturn("old_password");
        when(userService.findUserByUserName("username")).thenReturn(user);

        // Act
        HttpStatus status = authService.changeLogin(changeLoginRequest);

        // Assert
        assertEquals(HttpStatus.OK, status);
        verify(userService).changePassword("username", "new_password");
    }

//    @Test
//    void testChangeLogin_Unsuccessful() {
//        // Arrange
//        ChangeLoginRequest changeLoginRequest = new ChangeLoginRequest();
//        changeLoginRequest.setUsername("username");
//        changeLoginRequest.setOldPassword("old_password");
//        changeLoginRequest.setNewPassword("new_password");
//
//        User user = mock(User.class);
//        when(user.getPassword()).thenReturn("wrong_password");
//        when(userService.findUserByUserName("username")).thenReturn(user);
//
//        // Act
//        HttpStatus status = authService.changeLogin(changeLoginRequest);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, status);
//        verifyNoInteractions(userService);
//    }

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

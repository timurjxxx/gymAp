package com.example.gymAp.service;

import com.example.gymAp.dto.ChangeLoginRequest;
import com.example.gymAp.dto.JwtResponse;
import com.example.gymAp.dto.LoginRequest;
import com.example.gymAp.model.*;
import com.example.gymAp.security.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserService userService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final JWTProvider provider;
    private final LoginAttemptService loginAttemptService;

        private final PasswordEncoder encoder;

    public JwtResponse login(LoginRequest request) {
        if (loginAttemptService.isBlocked(request.getUsername())) {
            throw new BadCredentialsException("Your account is temporarily blocked. Please try again later.");
        }

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException ex) {
            loginAttemptService.loginFailed(request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
        if (decoderPassword(request.getPassword(), userDetails.getPassword())) {
            loginAttemptService.loginSucceeded(request.getUsername());
            String token = provider.generateToken(userDetails);
            return new JwtResponse(token);
        } else {
            loginAttemptService.loginFailed(request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    public HttpStatus changeLogin(ChangeLoginRequest request) {
        User user = userService.findUserByUserName(request.getUsername());
        if (decoderPassword(request.getOldPassword(), user.getPassword())) {
            userService.changePassword(request.getUsername(), request.getNewPassword());
            return HttpStatus.OK;

        } else {


            return HttpStatus.BAD_REQUEST;
        }

    }

    public String createTrainee(Trainee trainee) {
        String password = userService.generatePassword();
        trainee.getUser().setPassword(encoderPassword(password));
        Trainee createdTrainee = traineeService.createTrainee(trainee, trainee.getUser());
        return "Username :" + createdTrainee.getUser().getUserName() + " Password :" + password;

    }

    public String createTrainer(Trainer trainer) {
        String password = userService.generatePassword();
        trainer.getUser().setPassword(encoderPassword(password));
        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization().getTrainingTypeName());
        return "Username :" + createdTrainee.getUser().getUserName() + " Password :" + password;

    }

    private String encoderPassword(String password) {
        return encoder.encode(password);
    }

    private boolean decoderPassword(String rawPassword, String encoderPassword) {
        return encoder.matches(rawPassword, encoderPassword);
    }



}

package com.example.gymAp.service;

import com.example.gymAp.dto.ChangeLoginRequest;
import com.example.gymAp.dto.JwtResponse;
import com.example.gymAp.dto.LoginRequest;
import com.example.gymAp.model.*;
import com.example.gymAp.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserService userService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final JWTProvider provider;
//    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;


    public JwtResponse login(LoginRequest request) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//
//        } catch (BadCredentialsException e) {
//            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), " username or password"), HttpStatus.UNAUTHORIZED);
//        }


        if (loginAttemptService.isBlocked(request.getUsername())){
            throw new BadCredentialsException("Your account is temporarily blocked. Please try again later.");

        }

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        if (userDetails.getPassword().equals(request.getPassword())) {
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
        if (user.getPassword().equals(request.getOldPassword())) {
            userService.changePassword(request.getUsername(), request.getNewPassword());
            return HttpStatus.OK;

        } else {


            return HttpStatus.BAD_GATEWAY;
        }

    }

    public String createTrainee(Trainee trainee) {

        Trainee createdTrainee = traineeService.createTrainee(trainee, trainee.getUser());
        return "Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword();

    }

    public String createTrainer( Trainer trainer) {

        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization().getTrainingTypeName());
        return "Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword();

    }
}

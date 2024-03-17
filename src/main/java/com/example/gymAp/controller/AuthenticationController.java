package com.example.gymAp.controller;

import com.example.gymAp.exception.InvalidCredentialsException;
import com.example.gymAp.model.*;
import com.example.gymAp.service.TraineeService;
import com.example.gymAp.service.TrainerService;
import com.example.gymAp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {


    private final UserService userService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;


    @Autowired
    public AuthenticationController(UserService userService, TrainerService trainerService, TraineeService traineeService) {
        this.userService = userService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }


    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findUserByUserName(request.getUsername());
        if (!user.getPassword().equals(request.getPassword())) {

            throw new InvalidCredentialsException("Invalid password");
        }

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "/create_trainee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainee(@RequestBody Trainee trainee) {

        Trainee createdTrainee = traineeService.createTrainee(trainee, trainee.getUser());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }

    @PostMapping(value = "/create_trainer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainer(@RequestBody Trainer trainer) {

        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization().getTrainingTypeName());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }


    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLogin(@RequestBody ChangeLoginRequest request) {
        User user = userService.findUserByUserName(request.getUsername());
        if (user.getPassword().equals(request.getOldPassword())) {
            userService.changePassword(request.getUsername(), request.getNewPassword());
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.ok(HttpStatus.BAD_GATEWAY);
        }

    }
}


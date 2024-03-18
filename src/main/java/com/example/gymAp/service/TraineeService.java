package com.example.gymAp.service;

import com.example.gymAp.dao.TraineeDAO;
import com.example.gymAp.dao.TrainerDAO;
import com.example.gymAp.dao.UserDAO;
import com.example.gymAp.exception.UserNotFoundException;
import com.example.gymAp.model.Trainee;
import com.example.gymAp.model.Trainer;
import com.example.gymAp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@Slf4j
public class TraineeService {

    private final UserService userService;

    private final TraineeDAO traineeDAO;


    @Autowired
    public TraineeService(TraineeDAO traineeDAO, UserService userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }

    @Transactional
    public Trainee createTrainee(@Valid Trainee trainee, @NotNull User user) {
        trainee.setUser(userService.createUser(user));
        return traineeDAO.save(trainee);
    }

    @Transactional(readOnly = true)
    public Trainee selectTraineeByUserName(@NotBlank String username) throws UserNotFoundException {
        return traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainee with username: " + username + " is not found"));
    }

    @Transactional
    public Trainee updateTrainee(@NotBlank String username, @Valid Trainee updatedTrainee) throws UserNotFoundException {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainee with username: " + username + " is not found"));
        trainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
        trainee.setAddress(updatedTrainee.getAddress());
        trainee.setUser(userService.updateUser(updatedTrainee.getUser().getUserName(), updatedTrainee.getUser()));
        return traineeDAO.save(trainee);
    }

    @Transactional
    public void deleteTraineeByUserName(@NotBlank String username) throws UserNotFoundException {
        traineeDAO.findTraineeByUserUserName(username)
                .orElseThrow(() -> new UserNotFoundException("Trainee with username: " + username + " is not found"));

        traineeDAO.deleteTraineeByUserUserName(username);
        log.info("Deleted trainee with username: {}", username);
        log.debug("Deleted trainee with username {}  ", username);
    }

    @Transactional
    public Trainee updateTraineeTrainersList(@NotBlank String username, @NotBlank Set<Trainer> updatedList) throws UserNotFoundException {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainee with username: " + username + " is not found"));
        trainee.setTrainers(updatedList);
        log.info("Updated trainers list for trainee with username {}", username);
        log.debug("Updated trainee details: new trainers list {}", updatedList);

        return traineeDAO.save(trainee);
    }

    @Transactional
    public void changePassword(@NotBlank String username, @NotBlank String newPassword) throws UserNotFoundException {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainee with username: " + username + " is not found"));
        trainee.getUser().setPassword(userService.changePassword(username, newPassword));
        log.info("Changed password for trainee with username: {}", username);
    }

    @Transactional
    public void changeStatus(@NotBlank String username) throws UserNotFoundException {
        Trainee trainee = traineeDAO.findTraineeByUserUserName(username).orElseThrow(() -> new UserNotFoundException("Trainee with username: " + username + " is not found"));
        trainee.getUser().setIsActive(userService.changeStatus(username));
        log.info("Changed status for trainee with ID: {}", trainee.getId());
    }
}
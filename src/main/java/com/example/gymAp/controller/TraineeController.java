package com.example.gymAp.controller;

import com.example.gymAp.model.Trainee;
import com.example.gymAp.model.Trainer;
import com.example.gymAp.service.TraineeService;
import com.example.gymAp.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/trainee", produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public TraineeController(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }



    @GetMapping("/get_Trainee/{username}")
    public ResponseEntity<String> getTraineeProfile(@PathVariable("username") String username) {

        Trainee trainee = traineeService.selectTraineeByUserName(username);
        if (trainee != null) {
            return ResponseEntity.ok(trainee + trainee.getTrainers().toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @PutMapping(value = "/update_Trainee/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTraineeProfile(@PathVariable("username") String username, @RequestBody Trainee trainee) {
        Trainee updatedTrainee = traineeService.updateTrainee(trainee.getUser().getUserName(), trainee);
        return ResponseEntity.ok(updatedTrainee.toString() + updatedTrainee.getTrainers().toString());
    }

    @DeleteMapping("/delete_Trainee/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(@PathVariable("username") String username) {
        traineeService.deleteTraineeByUserName(username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/updateTrainersList/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTraineeTrainersList(@PathVariable("username") String username, @RequestBody Map<String, Object> jsonData) {
        String traineeUsername = (String) jsonData.get("traineeUsername");
        List<String> trainerUsernames = (List<String>) jsonData.get("trainerUsernames");
        Set<Trainer> trainers = new HashSet<>();
        for (String item : trainerUsernames) {
            trainers.add(trainerService.selectTrainerByUserName(item));
        }
        Trainee updatedTrainee = traineeService.updateTraineeTrainersList(traineeUsername, trainers);
        return ResponseEntity.ok(updatedTrainee.getTrainers().toString());
    }

    @PatchMapping(value = "/change_status/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateDeactivateTrainee(@PathVariable("username") String username, @RequestBody Map<String, String> jsonData) {
        traineeService.changeStatus(jsonData.get("username"));
        return ResponseEntity.ok().build();
    }

}

package com.example.gymAp.controller;

import com.example.gymAp.model.Training;
import com.example.gymAp.model.TrainingSearchCriteria;
import com.example.gymAp.service.TrainingService;
import com.example.gymAp.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/training", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainingController {

    private final TrainingService trainingService;


    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping(value = "/create_training", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTraining(@RequestBody Training training) {

        String trainerName = training.getTrainer().getUser().getUserName();
        String traineeName = training.getTrainee().getUser().getUserName();
        String trainingTypeName = training.getTrainingTypes().getTrainingTypeName();
        trainingService.addTraining(training, trainerName, traineeName, trainingTypeName);
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/trainee/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTraineeTrainingsByCriteria(@PathVariable("username") String username, @RequestBody TrainingSearchCriteria criteria) {

        List<Training> trainings = trainingService.getTraineeTrainingsByCriteria(username, criteria);
        return ResponseEntity.ok(trainings.toString());

    }

    @GetMapping(value = "/trainer/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTrainerTrainingsByCriteria(@PathVariable("username") String username, @RequestBody TrainingSearchCriteria criteria) {

        List<Training> trainings = trainingService.getTrainerTrainingsByCriteria(username, criteria);
        return ResponseEntity.ok(trainings.toString());

    }


}

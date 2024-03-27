package com.example.gymAp.controller;

import com.example.gymAp.model.TrainerWorkloadRequest;
import com.example.gymAp.model.Training;
import com.example.gymAp.model.TrainingSearchCriteria;
import com.example.gymAp.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(value = "/training", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final WebClient.Builder webClient;

    @PostMapping(value = "/create_training", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTraining(@RequestBody Training training) {
        String trainerName = training.getTrainer().getUser().getUserName();
        String traineeName = training.getTrainee().getUser().getUserName();
        String trainingTypeName = training.getTrainingTypes().getTrainingTypeName();
        log.info("Creating training with trainer: {}, trainee: {}, training type: {}", trainerName, traineeName, trainingTypeName);
        Training training1 = trainingService.addTraining(training, trainerName, traineeName, trainingTypeName);
        log.debug("Training created successfully");
//        TrainerWorkloadRequest request = TrainerWorkloadRequest.builder()
//                .trainerFirstname()
//                .trainerLastname()
//                .trainerUsername()
//                .isActive()
//                .trainingDuration()
//                .trainingDate()
//                .build();
//        webClient.build()
//                .post()
//                .uri("http://localhost:8082/updateWorkLoad/update")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(training))
//                .retrieve()
//                .bodyToMono(Void.class)
//                .subscribe();
        log.info("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEER TrainerWorkLoad request {}", training1.getTrainer());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/trainee/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTraineeTrainingsByCriteria(@PathVariable("username") String username, @RequestBody TrainingSearchCriteria criteria) {
        log.info("Fetching trainee trainings for username: {} with criteria: {}", username, criteria);
        List<Training> trainings = trainingService.getTraineeTrainingsByCriteria(username, criteria);
        log.debug("Fetched trainee trainings: {}", trainings);
        return ResponseEntity.ok(trainings.toString());
    }

    @GetMapping(value = "/trainer/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTrainerTrainingsByCriteria(@PathVariable("username") String username, @RequestBody TrainingSearchCriteria criteria) {
        log.info("Fetching trainer trainings for username: {} with criteria: {}", username, criteria);
        List<Training> trainings = trainingService.getTrainerTrainingsByCriteria(username, criteria);
        log.debug("Fetched trainer trainings: {}", trainings);
        return ResponseEntity.ok(trainings.toString());
    }
}

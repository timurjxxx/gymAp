package com.example.gymAp.controller;

import com.example.gymAp.model.Trainer;
import com.example.gymAp.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/trainer", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/get_Trainer/{username}")
    public ResponseEntity<String> getTrainerProfile(@PathVariable("username") String username) {
        log.info("Fetching trainer profile for username: {}", username);
        Trainer trainer = trainerService.selectTrainerByUserName(username);
        log.debug("Trainer details: {}", trainer);
        return ResponseEntity.ok(trainer.toString());
    }

    @PutMapping(value = "/update_Trainer/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTrainerProfile(@PathVariable("username") String username, @RequestBody Trainer trainer) {
        log.info("Updating trainer profile for username: {}", username);
        Trainer updatedTrainer = trainerService.updateTrainer(trainer.getUser().getUserName(), trainer);
        log.debug("Updated trainer details: {}", updatedTrainer);
        return ResponseEntity.ok(updatedTrainer.toString());
    }

    @GetMapping("/get_Trainers/{username}")
    public ResponseEntity<String> getNotAssignedActiveTrainers(@PathVariable("username") String username) {
        log.info("Fetching not assigned active trainers for username: {}", username);
        List<Trainer> trainers = trainerService.getNotAssignedActiveTrainers(username);
        log.debug("Fetched trainers: {}", trainers);
        return ResponseEntity.ok(trainers.toString());
    }

    @PatchMapping(value = "/change_status/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateDeactivateTrainer(@PathVariable("username") String username) {
        log.info("Activating/deactivating trainer with username: {}", username);
        trainerService.changeStatus(username);
        return ResponseEntity.ok().build();
    }
}

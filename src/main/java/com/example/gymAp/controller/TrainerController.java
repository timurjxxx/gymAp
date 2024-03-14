package com.example.gymAp.controller;

import com.example.gymAp.model.Trainer;
import com.example.gymAp.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/trainer", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainerController {


    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(value = "/create_trainer",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainer(@RequestBody Trainer trainer) {

        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization().getTrainingTypeName());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }

    @GetMapping("/get_Trainer/{username}")
    public ResponseEntity<String> getTrainerProfile(@PathVariable("username") String username) {

        Trainer trainer = trainerService.selectTrainerByUserName(username);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @PutMapping(value = "/update_Trainer/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTrainerProfile(@PathVariable("username") String username, @RequestBody Trainer trainer) {
        Trainer updatedTrainee = trainerService.updateTrainer(trainer.getUser().getUserName(), trainer);
        return ResponseEntity.ok(updatedTrainee.toString());
    }
    @GetMapping("/get_Trainers/{username}")
    public ResponseEntity<String> getNotAssignedActiveTrainers(@PathVariable("username") String username) {

        List<Trainer> trainer = trainerService.getNotAssignedActiveTrainers(username);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @PatchMapping(value = "/change_status/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateDeactivateTrainer(@PathVariable("username") String username, @RequestBody Map<String, String> jsonData) {
        trainerService.changeStatus(jsonData.get("username"));
        return ResponseEntity.ok().build();
    }

}
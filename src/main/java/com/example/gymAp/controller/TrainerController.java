package com.example.gymAp.controller;

import com.example.gymAp.aspect.Authenticated;
import com.example.gymAp.model.Trainer;
import com.example.gymAp.service.TrainerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/trainer", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Trainer Controller", description = "Operations related to trainer management")
public class TrainerController {


    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a new trainer", response = ResponseEntity.class)
    public ResponseEntity<String> createTrainer(@RequestBody Trainer trainer) {

        Trainer createdTrainee = trainerService.createTrainer(trainer, trainer.getUser(), trainer.getSpecialization().getTrainingTypeName());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }

    @Authenticated
    @GetMapping("/get_Trainer")
    @ApiOperation(value = "Get trainer profile by username", response = ResponseEntity.class)
    public ResponseEntity<String> getTrainerProfile(@RequestHeader("username") String username,@RequestHeader("password") String password) {

        Trainer trainer = trainerService.selectTrainerByUserName(username);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @Authenticated
    @PutMapping(value = "/update_Trainer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update trainer profile by username", response = ResponseEntity.class)
    public ResponseEntity<String> updateTrainerProfile(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody Trainer trainer) {
        Trainer updatedTrainee = trainerService.updateTrainer(trainer.getUser().getUserName(), trainer);
        return ResponseEntity.ok(updatedTrainee.toString());
    }
    @Authenticated
    @GetMapping("/get_Trainers")
    @ApiOperation(value = "Get not assigned active trainers", response = ResponseEntity.class)
    public ResponseEntity<String> getNotAssignedActiveTrainers(@RequestHeader("username") String userName,@RequestHeader("password") String password) {

        List<Trainer> trainer = trainerService.getNotAssignedActiveTrainers(userName);
        if (trainer != null) {
            return ResponseEntity.ok(trainer.toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @Authenticated
    @PatchMapping(value = "/change_status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Activate or deactivate trainer by username", response = ResponseEntity.class)
    public ResponseEntity<Void> activateDeactivateTrainer(@RequestHeader("username") String username,@RequestHeader("password") String password, @RequestBody Map<String, String> jsonData) {
        trainerService.changeStatus(jsonData.get("username"));
        return ResponseEntity.ok().build();
    }

}
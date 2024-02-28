package com.example.gymAp.controller;

import com.example.gymAp.aspect.Authenticated;
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
//@Api(value = "Trainee Controller", description = "Operations related to trainee management")
public class TraineeController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Autowired
    public TraineeController(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Create a new trainee", response = ResponseEntity.class)

    public ResponseEntity<String> createTrainee(@RequestBody Trainee trainee) {

        Trainee createdTrainee = traineeService.createTrainee(trainee, trainee.getUser());
        return ResponseEntity.ok("Username :" + createdTrainee.getUser().getUserName() + " Password :" + createdTrainee.getUser().getPassword());

    }

    @Authenticated
    @GetMapping("/get_Trainee")
//    @ApiOperation(value = "Get trainee profile by username", response = ResponseEntity.class)

    public ResponseEntity<String> getTraineeProfile(@RequestHeader("username") String username, @RequestHeader("password") String password) {

        Trainee trainee = traineeService.selectTraineeByUserName(username);
        if (trainee != null) {
            return ResponseEntity.ok(trainee + trainee.getTrainers().toString());

        } else {
            throw new EntityNotFoundException("Not found");
        }

    }

    @Authenticated
    @PutMapping(value = "/update_Trainee", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Update trainee profile by username", response = ResponseEntity.class)

    public ResponseEntity<String> updateTraineeProfile(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody Trainee trainee) {
        Trainee updatedTrainee = traineeService.updateTrainee(trainee.getUser().getUserName(), trainee);
        return ResponseEntity.ok(updatedTrainee.toString() + updatedTrainee.getTrainers().toString());
    }

    @Authenticated
    @DeleteMapping("/delete_Trainee")
//    @ApiOperation(value = "Delete trainee profile by username", response = ResponseEntity.class)

    public ResponseEntity<Void> deleteTraineeProfile(@RequestHeader("username") String username, @RequestHeader("password") String password) {
        traineeService.deleteTraineeByUserName(username);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @PutMapping(value = "/updateTrainersList", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Update trainee trainers list by username", response = ResponseEntity.class)

    public ResponseEntity<String> updateTraineeTrainersList(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody Map<String, Object> jsonData) {
        String traineeUsername = (String) jsonData.get("traineeUsername");
        List<String> trainerUsernames = (List<String>) jsonData.get("trainerUsernames");
        Set<Trainer> trainers = new HashSet<>();
        for (String item : trainerUsernames) {
            trainers.add(trainerService.selectTrainerByUserName(item));
        }
        Trainee updatedTrainee = traineeService.updateTraineeTrainersList(traineeUsername, trainers);
        return ResponseEntity.ok(updatedTrainee.getTrainers().toString());
    }

    @Authenticated
    @PatchMapping(value = "/change_status", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Activate or deactivate trainee by username", response = ResponseEntity.class)

    public ResponseEntity<Void> activateDeactivateTrainee(@RequestHeader("username") String username, @RequestHeader("password") String password, @RequestBody Map<String, String> jsonData) {
        traineeService.changeStatus(jsonData.get("username"));
        return ResponseEntity.ok().build();
    }

}

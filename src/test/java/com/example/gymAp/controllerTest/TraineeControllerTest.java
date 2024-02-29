package com.example.gymAp.controllerTest;

import com.example.gymAp.controller.TraineeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.gymAp.controller.TrainingController;
import com.example.gymAp.dao.TrainingDAO;
import com.example.gymAp.model.*;
import com.example.gymAp.service.TraineeService;
import com.example.gymAp.service.TrainerService;
import com.example.gymAp.service.TrainingService;
import com.example.gymAp.service.TrainingTypeService;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrainee() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName("user");
        user.setLastName("last name");
        trainee.setUser(user);
        when(traineeService.createTrainee(any(Trainee.class), any())).thenReturn(trainee);

        ResponseEntity<String> response = traineeController.createTrainee(trainee);

        assertEquals(ResponseEntity.ok("Username :" + trainee.getUser().getUserName() +
                " Password :" + trainee.getUser().getPassword()), response);
    }

    @Test
    void getTraineeProfile() {
        String username = "testUser";
        String password = "testPassword";
        Trainee trainee = new Trainee();
        when(traineeService.selectTraineeByUserName(username)).thenReturn(trainee);

        ResponseEntity<String> response = traineeController.getTraineeProfile(username, password);

        assertEquals(ResponseEntity.ok(trainee + trainee.getTrainers().toString()), response);
    }

    @Test
    public void testUpdateTraineeProfile() {

        Trainee trainer = new Trainee();
        trainer.setUser(new User());
        Set<Trainer> set = new HashSet<>();
        trainer.setTrainers(set);

        when(traineeService.updateTrainee(Mockito.any(), Mockito.any())).thenReturn(trainer);

        ResponseEntity<String> response = traineeController.updateTraineeProfile("username", "password", trainer);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trainer.toString() + trainer.getTrainers().toString(), response.getBody());
    }

    @Test
    void deleteTraineeProfile() {
        String username = "testUser";
        String password = "testPassword";

        ResponseEntity<Void> response = traineeController.deleteTraineeProfile(username, password);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(traineeService, times(1)).deleteTraineeByUserName(username);
    }

    @Test
    void updateTraineeTrainersList_shouldReturnUpdatedTrainee() {
        String username = "traineeUsername";
        String password = "traineePassword";
        String traineeUsername = "traineeUsername";
        List<String> trainerUsernames = Arrays.asList("trainer1", "trainer2");

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("traineeUsername", traineeUsername);
        jsonData.put("trainerUsernames", trainerUsernames);

        Set<Trainer> trainers = new HashSet<>();
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        trainers.add(trainer1);
        trainers.add(trainer2);

        when(trainerService.selectTrainerByUserName("trainer1")).thenReturn(trainer1);
        when(trainerService.selectTrainerByUserName("trainer2")).thenReturn(trainer2);

        Trainee updatedTrainee = new Trainee();
        when(traineeService.updateTraineeTrainersList(traineeUsername, trainers)).thenReturn(updatedTrainee);

        ResponseEntity<String> response = traineeController.updateTraineeTrainersList(username, password, jsonData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTrainee.getTrainers().toString(), response.getBody());

        verify(trainerService, times(2)).selectTrainerByUserName(anyString());
        verify(traineeService, times(1)).updateTraineeTrainersList(traineeUsername, trainers);
    }


    @Test
    void activateDeactivateTrainee() {
        String username = "testUser";
        String password = "testPassword";
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("username", username);

        ResponseEntity<Void> response = traineeController.activateDeactivateTrainee(username, password, jsonData);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(traineeService, times(1)).changeStatus(username);
    }
}
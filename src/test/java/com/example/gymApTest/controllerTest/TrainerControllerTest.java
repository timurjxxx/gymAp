package com.example.gymApTest.controllerTest;

import com.example.gymAp.controller.TrainerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.example.gymAp.controller.TrainingController;
import com.example.gymAp.dao.TrainingDAO;
import com.example.gymAp.model.*;
import com.example.gymAp.service.TraineeService;
import com.example.gymAp.service.TrainerService;
import com.example.gymAp.service.TrainingService;
import com.example.gymAp.service.TrainingTypeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetTrainerProfile() {
        String username = "testUser";
        String password = "testPassword";
        Trainer trainer = new Trainer();
        trainer.setSpecialization(new TrainingType());
        trainer.setUser(new User());
        when(trainerService.selectTrainerByUserName(username)).thenReturn(trainer);

        ResponseEntity<String> response = trainerController.getTrainerProfile(username);

        assertEquals(ResponseEntity.ok(trainer.toString()), response);
    }

    @Test
    public void testUpdateTrainerProfile() {

        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.setSpecialization(new TrainingType());

        when(trainerService.updateTrainer(Mockito.any(), Mockito.any())).thenReturn(trainer);

        ResponseEntity<String> response = trainerController.updateTrainerProfile("username",  trainer);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(trainer.toString(), response.getBody());
    }

    @Test
    void getNotAssignedActiveTrainers() {
        String userName = "user";
        String password = "pass";
        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        trainer.setSpecialization(new TrainingType());
        Trainer trainer2 = new Trainer();
        trainer2.setUser(new User());
        trainer2.setSpecialization(new TrainingType());

        List<Trainer> trainers = new ArrayList<>();
        trainers.add(trainer);
        trainers.add(trainer2);
        when(trainerService.getNotAssignedActiveTrainers(userName)).thenReturn(trainers);

        ResponseEntity<String> response = trainerController.getNotAssignedActiveTrainers(userName);

        assertEquals(ResponseEntity.ok(trainers.toString()), response);
        verify(trainerService, times(1)).getNotAssignedActiveTrainers(userName);
    }

    @Test
    public void testActivateDeactivateTrainer() {
        TrainerService trainerServiceMock = mock(TrainerService.class);
        TrainerController trainerController = new TrainerController(trainerServiceMock);

        Map<String, String> jsonData = Collections.singletonMap("username", "someUsername");
        ResponseEntity<Void> response = trainerController.activateDeactivateTrainer("username",  jsonData);
        assertEquals(200, response.getStatusCodeValue());
    }
}
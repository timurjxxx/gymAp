//package com.example.gymApTest.controllerTest;
//
//import com.example.gymAp.controller.AuthenticationController;
//import com.example.gymAp.controller.TrainerController;
//import com.example.gymAp.model.Trainee;
//import com.example.gymAp.model.Trainer;
//import com.example.gymAp.model.TrainingType;
//import com.example.gymAp.model.User;
//import com.example.gymAp.service.TrainerService;
//import com.example.gymAp.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class AuthenticationControllerTest {
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private AuthenticationController authenticationController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void createTrainee() {
//        Trainee trainee = new Trainee();
//        User user = new User();
//        user.setFirstName("user");
//        user.setLastName("last name");
//        trainee.setUser(user);
//        when(traineeService.createTrainee(any(Trainee.class), any())).thenReturn(trainee);
//
//        ResponseEntity<String> response = traineeController.createTrainee(trainee);
//
//        assertEquals(ResponseEntity.ok("Username :" + trainee.getUser().getUserName() +
//                " Password :" + trainee.getUser().getPassword()), response);
//    }
//
//
//    @Test
//    public void testCreateTrainer() {
//        TrainerService trainerServiceMock = mock(TrainerService.class);
//        TrainerController trainerController = new TrainerController(trainerServiceMock);
//
//        Trainer trainer = new Trainer();
//        User user = new User();
//        user.setFirstName("user");
//        user.setLastName("last name");
//        trainer.setUser(user);
//        trainer.setUser(new User());
//        trainer.setSpecialization(new TrainingType());
//
//        when(trainerServiceMock.createTrainer(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(trainer);
//
//        ResponseEntity<String> response = trainerController.createTrainer(trainer);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Username :" + trainer.getUser().getUserName() + " Password :" + trainer.getUser().getPassword(), response.getBody());
//    }
//
//
//}

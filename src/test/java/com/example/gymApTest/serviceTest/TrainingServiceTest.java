package com.example.gymApTest.serviceTest;

import com.example.gymAp.dao.TrainingDAO;
import com.example.gymAp.model.*;
import com.example.gymAp.service.TraineeService;
import com.example.gymAp.service.TrainerService;
import com.example.gymAp.service.TrainingService;
import com.example.gymAp.service.TrainingTypeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @Captor
    private ArgumentCaptor<Specification<Training>> specificationCaptor;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;


    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraining() {
        Long trainerId = 1L;
        Long traineeId = 2L;
        String trainingTypeName = "Cardio";

        Training training = new Training();
        training.setTrainingName("Sample Training");

        Trainer trainer = new Trainer();
        trainer.setId(trainerId);

        Trainee trainee = new Trainee();
        trainee.setId(traineeId);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(3L);

        when(trainerService.selectTrainerByUserName(anyString())).thenReturn(trainer);
        when(traineeService.selectTraineeByUserName(anyString())).thenReturn(trainee);
        when(trainingTypeService.findByTrainingName(anyString())).thenReturn(trainingType);

        when(trainingDAO.save(any(Training.class))).thenAnswer(invocation -> {
            Training savedTraining = invocation.getArgument(0);
            savedTraining.setId(1L);
            return savedTraining;
        });

        Training result = trainingService.addTraining(training, "trainerName", "traineeName", trainingTypeName);

        assertNotNull(result.getId());
        assertEquals(training.getTrainingName(), result.getTrainingName());

        verify(trainerService, times(1)).selectTrainerByUserName("trainerName");
        verify(traineeService, times(1)).selectTraineeByUserName("traineeName");
        verify(trainingTypeService, times(1)).findByTrainingName(trainingTypeName);
        verify(trainingDAO, times(1)).save(any(Training.class));
    }
    @Test
    void testGetTraineeTrainingsByCriteria() {
        String traineeUsername = "john.trainee";

        Trainee trainee = new Trainee();
        trainee.setId(1L);

        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName("Sample Training");
        criteria.setTrainingStartDate(LocalDate.of(2023, 1, 1));
        criteria.setTrainingEndDate(LocalDate.of(2023, 12, 31));
        criteria.setTrainingDuration(30);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Cardio");

        criteria.setTrainingTypes(trainingType);

        List<Training> mockTrainings = Arrays.asList(new Training(), new Training());

        when(traineeService.selectTraineeByUserName(traineeUsername)).thenReturn(trainee);
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(mockTrainings);

        List<Training> result = trainingService.getTraineeTrainingsByCriteria(traineeUsername, criteria);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(traineeService, times(1)).selectTraineeByUserName(traineeUsername);
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());

    }


    @Test
    void testGetTrainerTrainingsByCriteria() {
        String trainerUsername = "jane.trainer";

        Trainer trainer = new Trainer();
        trainer.setId(1L);

        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName("Sample Training");
        criteria.setTrainingStartDate(LocalDate.of(2023, 1, 1));
        criteria.setTrainingEndDate(LocalDate.of(2023, 12, 31));
        criteria.setTrainingDuration(30);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Strength");

        criteria.setTrainingTypes(trainingType);

        List<Training> mockTrainings = Arrays.asList(new Training(), new Training());

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(trainer);
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(mockTrainings);

        List<Training> result = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(trainerService, times(1)).selectTrainerByUserName(trainerUsername);
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());

    }



    @Test
    public void testGetTrainerTrainingsByCriteria_WithTrainingName() {
        // Arrange
        String trainerUsername = "trainer1";
        String trainingName = "Training 1";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName(trainingName);

        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);
        trainer.getUser().setUserName(trainerUsername);

        Training training = new Training();
        training.setTrainingName(trainingName);
        List<Training> expectedTrainings = new ArrayList<>();
        expectedTrainings.add(training);

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(trainer);
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        assertEquals(expectedTrainings, actualTrainings);
    }


    @Test
    public void testGetTrainerTrainingsByCriteria_WithStartDateAndEndDate() {
        String trainerUsername = "trainer1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingStartDate(startDate);
        criteria.setTrainingEndDate(endDate);

        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);
        trainer.getUser().setUserName(trainerUsername);

        Training training1 = new Training();
        training1.setTrainingDate(startDate);
        Training training2 = new Training();
        training2.setTrainingDate(endDate);

        List<Training> expectedTrainings = new ArrayList<>();
        expectedTrainings.add(training1);
        expectedTrainings.add(training2);

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(trainer);
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        assertEquals(expectedTrainings, actualTrainings);
    }

    @Test
    public void testGetTrainerTrainingsByCriteria_WithTrainingDuration() {
        String trainerUsername = "trainer1";
        int trainingDuration = 60;
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingDuration(trainingDuration);

        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);
        trainer.getUser().setUserName(trainerUsername);

        Training training1 = new Training();
        training1.setTrainingDuration(trainingDuration);
        Training training2 = new Training();
        training2.setTrainingDuration(trainingDuration);

        List<Training> expectedTrainings = new ArrayList<>();
        expectedTrainings.add(training1);
        expectedTrainings.add(training2);

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(trainer);
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        assertEquals(expectedTrainings, actualTrainings);
    }

    @Test
    public void testGetTrainerTrainingsByCriteria_WithTrainingTypes() {
        String trainerUsername = "trainer1";
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Cardio");
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingTypes(trainingType);

        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);
        trainer.getUser().setUserName(trainerUsername);

        Training training1 = new Training();
        training1.setTrainingTypes(trainingType);
        Training training2 = new Training();
        training2.setTrainingTypes(trainingType);

        List<Training> expectedTrainings = new ArrayList<>();
        expectedTrainings.add(training1);
        expectedTrainings.add(training2);

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(trainer);
        when(trainingDAO.findAll((Specification<Training>) any())).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        assertEquals(expectedTrainings, actualTrainings);
    }


    @Test
    public void testGetTrainerTrainingsByCriteria1() {
        // Arrange
        String trainerUsername = "trainer1";
        String trainingName = "Training 1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);
        int trainingDuration = 60; // minutes

        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);
        trainer.getUser().setUserName(trainerUsername);

        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingName(trainingName);
        criteria.setTrainingStartDate(startDate);
        criteria.setTrainingEndDate(endDate);
        criteria.setTrainingDuration(trainingDuration);

        Root<Training> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        List<Predicate> predicates = new ArrayList<>();

        when(trainerService.selectTrainerByUserName(trainerUsername)).thenReturn(trainer);
        when(cb.equal(root.get("trainer"), trainer)).thenReturn(mock(Predicate.class));
        when(cb.equal(root.get("trainingName"), trainingName)).thenReturn(mock(Predicate.class));
        when(cb.between(root.get("trainingDate"), startDate, endDate)).thenReturn(mock(Predicate.class));
        when(cb.equal(root.get("trainingDuration"), trainingDuration)).thenReturn(mock(Predicate.class));
        when(cb.and(any())).thenReturn(mock(Predicate.class));
        when(root.get((SingularAttribute<? super Training, Object>) any())).thenReturn(null);

        // Act
        trainingService.getTrainerTrainingsByCriteria(trainerUsername, criteria);

        // Assert
        verify(trainingDAO, times(1)).findAll((Specification<Training>) any());
    }
}
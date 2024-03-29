package com.example.gymAp.service;

import com.example.gymAp.dao.TrainingDAO;
import com.example.gymAp.model.*;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;

@Service
@Slf4j
public class TrainingService {


    private final TrainingDAO trainingDAO;
    private final TrainerService trainerService;
    private final TraineeService traineeService;

    private final TrainingTypeService trainingTypeService;


    @Autowired
    public TrainingService(TrainingDAO trainingDAO, TrainerService trainerService, TraineeService traineeService, TrainingTypeService trainingTypeService) {
        this.trainingDAO = trainingDAO;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingTypeService = trainingTypeService;
    }

    @Transactional
    public Training addTraining(Training training, String trainerName, String traineeName, String trainingTypeName) {
        training.setTrainer(trainerService.selectTrainerByUserName(trainerName));
        training.setTrainee(traineeService.selectTraineeByUserName(traineeName));
        training.setTrainingTypes(trainingTypeService.findByTrainingName(trainingTypeName));
        log.info("Added training with trainer name {}, and trainee name {} and trainingtype {}", trainerName, traineeName, trainingTypeName);
        log.debug("Added training details: {}", training);

        return trainingDAO.save(training);
    }

    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, TrainingSearchCriteria criteria) {
        Trainer trainer = trainerService.selectTrainerByUserName(trainerUsername);
        log.info("Get trainer with username: {}", trainerUsername);
        log.debug("Trainer details: {}", trainer);
        log.debug("Criteria details: {}", criteria);

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(trainer).ifPresent(t -> predicates.add(cb.equal(root.get("trainer"), t)));

            Optional.ofNullable(criteria).ifPresent(c -> {
                Optional.ofNullable(c.getTrainingName()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingName"), v)));
                if (c.getTrainingStartDate() != null && c.getTrainingEndDate() != null) {
                    predicates.add(cb.between(root.get("trainingDate"), c.getTrainingStartDate(), c.getTrainingEndDate()));
                }
                Optional.ofNullable(c.getTrainingDuration()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingDuration"), v)));

                Optional.ofNullable(c.getTrainingTypes()).ifPresent(trainingType -> {
                    Join<Training, TrainingType> trainingTypeJoin = root.join("trainingType");
                    predicates.add(cb.equal(trainingTypeJoin.get("trainingTypeName"), trainingType.getTrainingTypeName()));
                });
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }


    public List<Training> getTraineeTrainingsByCriteria(String traineeUsername, TrainingSearchCriteria criteria) {
        Trainee trainee = traineeService.selectTraineeByUserName(traineeUsername);

        log.info("Get trainee with username: {}", traineeUsername);
        log.debug("Trainee details: {}", trainee);
        log.debug("Criterie details: {}", criteria);

        return trainingDAO.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(trainee).ifPresent(t -> predicates.add(cb.equal(root.get("trainee"), t)));

            Optional.ofNullable(criteria).ifPresent(c -> {
                Optional.ofNullable(c.getTrainingName()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingName"), v)));
                if (c.getTrainingStartDate() != null && c.getTrainingEndDate() != null) {
                    predicates.add(cb.between(root.get("trainingDate"), c.getTrainingStartDate(), c.getTrainingEndDate()));
                }
                Optional.ofNullable(c.getTrainingDuration()).ifPresent(v -> predicates.add(cb.equal(root.get("trainingDuration"), v)));

                Optional.ofNullable(c.getTrainingTypes()).ifPresent(trainingType -> {
                    Join<Training, TrainingType> trainingTypeJoin = root.join("trainingType", JoinType.LEFT);
                    predicates.add(cb.equal(trainingTypeJoin.get("trainingTypeName"), trainingType.getTrainingTypeName()));
                });
            });
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
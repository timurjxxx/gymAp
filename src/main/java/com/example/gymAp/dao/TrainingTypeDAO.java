package com.example.gymAp.dao;

import com.example.gymAp.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeDAO extends JpaRepository<TrainingType, Long> {


    TrainingType findTrainingTypeByTrainingTypeName(String name);

}

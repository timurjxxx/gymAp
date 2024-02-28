package com.example.gymAp.service;

import com.example.gymAp.dao.TrainingTypeDAO;
import com.example.gymAp.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService {
    private final TrainingTypeDAO trainingTypeDAO;

    @Autowired
    public TrainingTypeService(TrainingTypeDAO trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }

    public TrainingType findByTrainingName(String name) {
        return trainingTypeDAO.findTrainingTypeByTrainingTypeName(name);
    }
    public List<TrainingType> getAll() {
        return trainingTypeDAO.findAll();
    }
}


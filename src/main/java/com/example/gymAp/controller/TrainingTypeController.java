package com.example.gymAp.controller;

import com.example.gymAp.model.TrainingType;
import com.example.gymAp.service.TrainingTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/trainingType", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {
        log.info("Fetching all training types");
        List<TrainingType> trainingTypes = trainingTypeService.getAll();
        log.debug("Fetched training types: {}", trainingTypes);
        return ResponseEntity.ok(trainingTypes);
    }
}

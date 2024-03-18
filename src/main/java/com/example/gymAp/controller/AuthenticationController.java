package com.example.gymAp.controller;

import com.example.gymAp.dto.ChangeLoginRequest;
import com.example.gymAp.dto.LoginRequest;
import com.example.gymAp.model.*;
import com.example.gymAp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthService service;


    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping(value = "/create_trainee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainee(@RequestBody Trainee trainee) {
        return ResponseEntity.ok(service.createTrainee(trainee));

    }

    @PostMapping(value = "/create_trainer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTrainer(@RequestBody Trainer trainer) {
        return ResponseEntity.ok(service.createTrainer(trainer));

    }


    @PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeLogin(@RequestBody ChangeLoginRequest request) {


        return ResponseEntity.ok(service.changeLogin(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return ResponseEntity.ok(service.logout(request));
    }
}


package com.example.gymAp.service;

import com.example.gymAp.dao.RolesDAO;
import com.example.gymAp.model.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private final RolesDAO roleRepository;

    public Roles getUserRole() {
        log.info("Get roles by name");
        return roleRepository.findByName("ROLE_USER").get();
    }
}
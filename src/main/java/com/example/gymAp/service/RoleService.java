package com.example.gymAp.service;

import com.example.gymAp.dao.RolesDAO;
import com.example.gymAp.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RolesDAO roleRepository;

    public Roles getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
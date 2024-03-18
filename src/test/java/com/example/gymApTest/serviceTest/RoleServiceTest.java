package com.example.gymApTest.serviceTest;

import com.example.gymAp.dao.RolesDAO;
import com.example.gymAp.model.Roles;
import com.example.gymAp.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    @Test
    public void testGetUserRole() {
        // Mock the RolesDAO dependency
        RolesDAO roleRepository = Mockito.mock(RolesDAO.class);

        // Create an instance of RoleService with the mocked dependency
        RoleService roleService = new RoleService(roleRepository);

        // Define the expected role
        Roles expectedRole = new Roles();
        expectedRole.setId(1);
        expectedRole.setName("ROLE_USER");

        // Mock the behavior of the RolesDAO method
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(expectedRole));

        // Call the getUserRole method
        Roles actualRole = roleService.getUserRole();

        // Verify that the method returns the expected role
        assertEquals(expectedRole, actualRole);
    }
}

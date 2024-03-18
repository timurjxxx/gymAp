package com.example.gymApTest.serviceTest;

import com.example.gymAp.dao.UserDAO;
import com.example.gymAp.exception.UserNotFoundException;
import com.example.gymAp.model.Roles;
import com.example.gymAp.model.User;
import com.example.gymAp.service.RoleService;
import com.example.gymAp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;




    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testCreateUser() {
        // Arrange
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setIsActive(true);

        Roles userRole = new Roles();
        userRole.setName("ROLE_USER");

        when(roleService.getUserRole()).thenReturn(userRole);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userDAO.save(any(User.class))).thenReturn(newUser);

        // Act
        User savedUser = userService.createUser(newUser);

        // Assert
        assertEquals("John.Doe", savedUser.getUserName());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(true, savedUser.getIsActive());
        verify(roleService, times(1)).getUserRole();
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userDAO, times(1)).save(any(User.class));
    }


    @Test
    void testFindUserByUserName() {
        Long userId = 1L;
        User sampleUser = new User();
        sampleUser.setId(userId);
        sampleUser.setFirstName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setUserName("johndoe");
        sampleUser.setPassword("password");
        sampleUser.setIsActive(true);

        when(userDAO.findUserByUserName("johndoe")).thenReturn(Optional.of(sampleUser));

        User foundUser = userService.findUserByUserName("johndoe");

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("John", foundUser.getFirstName());
        assertEquals("Doe", foundUser.getLastName());
        assertEquals("johndoe", foundUser.getUserName());
        assertEquals("password", foundUser.getPassword());
        assertTrue(foundUser.getIsActive());

        verify(userDAO, times(1)).findUserByUserName("johndoe");
    }


    @Test
    void testUpdateUser() {
        String userName = "john.doe";
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setUserName(userName);
        existingUser.setPassword("password");

        User updatedUser = new User();
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setUserName("updated.user");
        updatedUser.setPassword("password");

        when(userDAO.findUserByUserName(userName)).thenReturn(Optional.of(existingUser));
        when(userDAO.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userName, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(updatedUser.getLastName(), result.getLastName());
        assertEquals(updatedUser.getUserName(), result.getUserName());

        verify(userDAO, times(1)).findUserByUserName(userName);
        verify(userDAO, times(1)).save(any(User.class));
    }
    @Test
    void testDeleteWithNonExistingUser() {
        Long nonExistentUserId = 999L;

        when(userDAO.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(nonExistentUserId));

        verify(userDAO, times(1)).findById(nonExistentUserId);
        verify(userDAO, never()).deleteById(any());
    }

    @Test
    void testDelete() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setUserName("john.doe");
        existingUser.setPassword("password");

        when(userDAO.findById(userId)).thenReturn(Optional.of(existingUser));

        assertDoesNotThrow(() -> userService.delete(userId));

        verify(userDAO, times(1)).findById(userId);
        verify(userDAO, times(1)).deleteById(userId);
    }

    @Test
    void testChangePassword() {

        String username = "testUser";
        String newPassword = "newPassword";

        User existingUser = new User();
        existingUser.setUserName(username);

        when(userDAO.findUserByUserName(eq(username))).thenReturn(Optional.of(existingUser));
        when(userDAO.save(any())).thenReturn(existingUser);

        String changedPassword = userService.changePassword(username, newPassword);

        assertNotNull(changedPassword);
        assertEquals(newPassword, changedPassword);
        verify(userDAO, times(1)).findUserByUserName(eq(username));
        verify(userDAO, times(1)).save(any());
    }

    @Test
    void testChangeStatus() {

        String username = "testUser";

        User existingUser = new User();
        existingUser.setUserName(username);
        existingUser.setIsActive(true);

        when(userDAO.findUserByUserName(eq(username))).thenReturn(Optional.of(existingUser));
        when(userDAO.save(any())).thenReturn(existingUser);

        boolean newStatus = userService.changeStatus(username);

        assertFalse(newStatus);
        verify(userDAO, times(1)).findUserByUserName(eq(username));
        verify(userDAO, times(1)).save(any());
    }

    @Test
    void testGenerateUsername() {
        String baseUsername = "testUser";
        when(userDAO.findUserByUserName(anyString())).thenReturn(Optional.empty());

        String generatedUsername = userService.generateUsername(baseUsername);

        assertNotNull(generatedUsername);
        assertTrue(generatedUsername.startsWith(baseUsername));
        verify(userDAO, atLeastOnce()).findUserByUserName(anyString());
    }


    @Test
    void testGeneratePassword() {
        String generatedPassword = userService.generatePassword();

        assertNotNull(generatedPassword);
        assertEquals(0, generatedPassword.length());
        assertTrue(generatedPassword.matches("[A-Za-z0-9]{0}"));
    }
    @Test
    public void testAuthenticated_ValidCredentials() {
        String username = "validUsername";
        String password = "validPassword";


    }

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUserName(username);
        user.setPassword("testPassword");

        Roles role = new Roles();
        role.setName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        when(userDAO.findUserByUserName(username)).thenReturn(java.util.Optional.of(user));

        // Act
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Assert
        assertEquals(username, userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());

        verify(userDAO, times(1)).findUserByUserName(username);
    }

}
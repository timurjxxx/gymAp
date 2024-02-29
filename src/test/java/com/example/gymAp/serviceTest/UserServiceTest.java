package com.example.gymAp.serviceTest;

import com.example.gymAp.dao.UserDAO;
import com.example.gymAp.exception.UserNotFoundException;
import com.example.gymAp.model.User;
import com.example.gymAp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;


    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testSelectUser() {
        // Mocking
        Long userId = 1L;
        User sampleUser = new User();
        sampleUser.setId(userId);
        sampleUser.setFirstName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setUserName("johndoe");
        sampleUser.setPassword("password");
        sampleUser.setIsActive(true);

        when(userDAO.findById(anyLong())).thenReturn(Optional.of(sampleUser));

        User user = userService.selectUser(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        verify(userDAO, times(1)).findById(anyLong());
    }
    @Test
    void testCreateUser() {


        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setUserName("John.Doe");
        newUser.setIsActive(true);

        when(userDAO.save(any())).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("John.Doe", createdUser.getUserName());
        assertTrue(createdUser.getIsActive());
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
        UserDAO userDAO = mock(UserDAO.class);
        UserService userService = new UserService(userDAO);

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
        assertEquals(10, generatedPassword.length());
        assertTrue(generatedPassword.matches("[A-Za-z0-9]{10}"));
    }
    @Test
    public void testAuthenticated_ValidCredentials() {
        // Arrange
        String username = "validUsername";
        String password = "validPassword";

        // Act
        userService.authenticated(username, password);

        // Assert
    }

}
package com.example.gymAp.service;

import com.example.gymAp.dao.UserDAO;
import com.example.gymAp.exception.UserNotFoundException;
import com.example.gymAp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Random;
import java.util.stream.IntStream;

@Service

public class UserService {

    private final UserDAO userDAO;
    @Value("${app.user.password.chars}")
    private String passwordChars;

    @Value("${app.user.password.length}")
    private int passwordLength;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User selectUser(@NotBlank Long userId) {
        return userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException("User is not found"));
    }

    @Transactional(readOnly = true)
    public User findUserByUserName(String username) {
        LOGGER.info("Finding user by username: {}", username);

        return userDAO.findUserByUserName(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + "is not found "));
    }

    @Transactional
    public User createUser(@Valid User newUser) {
        newUser.setPassword(generatePassword());
        newUser.setUserName(generateUsername(newUser.getFirstName() + "." + newUser.getLastName()));
        User savedUser = userDAO.save(newUser);

        LOGGER.info("Created user with ID: {}", savedUser.getId());
        LOGGER.debug("Created user details: {}", savedUser);

        return userDAO.save(newUser);
    }

    @Transactional
    public User updateUser(@NotNull String userName, @Valid User updatedUser) {
        User user = userDAO.findUserByUserName(userName).orElseThrow(() -> new UserNotFoundException("User with username " + userName + "is not found "));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setIsActive(updatedUser.getIsActive());
        return userDAO.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userDAO.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + "is not found "));
        LOGGER.info("Deleting user with ID: {}", id);
        userDAO.deleteById(id);
    }

    @Transactional
    public String changePassword(@NotBlank String username, @NotBlank String newPassword) {
        LOGGER.info("Changing password for user: {}", username);

        User user = userDAO.findUserByUserName(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + "is not found "));
        user.setPassword(newPassword);
        userDAO.save(user);
        return newPassword;
    }

    @Transactional
    public boolean changeStatus(@NotBlank String username) {
        LOGGER.info("Deactivating user: {}", username);

        User user = userDAO.findUserByUserName(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + "is not found "));
        user.setIsActive(!user.getIsActive());
        userDAO.save(user);
        return user.getIsActive();
    }

    public String generateUsername(String username1) {
        LOGGER.info("Generating username");
        LOGGER.debug("Base username: {}", username1);

        return IntStream.iterate(1, i -> i + 1)
                .mapToObj(serialNumber -> username1 + ((serialNumber == 1) ? "" : "." + serialNumber))
                .filter(username -> !userDAO.findUserByUserName(username).isPresent())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public String generatePassword() {
        Random random = new Random();
        String password = IntStream.range(0, passwordLength)
                .mapToObj(i -> passwordChars.charAt(random.nextInt(passwordChars.length())))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        LOGGER.info("Generating unique password");
        LOGGER.debug("Generated password: {}", password);

        return password;
    }


}

package com.example.gymAp.aspect;

import com.example.gymAp.dao.UserDAO;
import com.example.gymAp.exception.InvalidCredentialsException;
import com.example.gymAp.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

    private final UserDAO userDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationAspect.class);

    public AuthenticationAspect(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Before("@within(com.example.gymAp.aspect.Authenticated) || @annotation(com.example.gymAp.aspect.Authenticated)")
    public void authenticate(JoinPoint joinPoint) {

        LOGGER.info("Authentication aspect is invoked.");


        Object[] args = joinPoint.getArgs();
        if (args.length >= 2 &&
                args[0] instanceof String &&
                args[1] instanceof String) {

            String username = (String) args[0];
            String password = (String) args[1];

            User user = userDAO.findUserByUserName(username).orElseThrow(EntityNotFoundException::new);

            if (user == null || !user.getPassword().equals(password)) {
                LOGGER.warn("Authentication failed for user: {}", username);
                throw new InvalidCredentialsException("Invalid username or password");
            }
        } else {
            LOGGER.error("Method requires at least two String parameters for authentication.");
            throw new IllegalArgumentException("Please provide username and password");
        }
    }

}

//package com.example.gymAp.utils;
//
//import com.example.gymAp.service.UserService;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserServiceHealthIndicator  implements HealthIndicator {
//    private final UserService userService;
//
//    public UserServiceHealthIndicator(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public Health health() {
//        try {
//            userService.authenticated("Test.Walk.2", "OAhPQBYEo6");
//            return Health.up().withDetail("message", "UserService.authenticated is UP").build();
//        } catch (Exception e) {
//            return Health.down().withDetail("message", "UserService.authenticated is DOWN").build();
//        }
//    }
//}

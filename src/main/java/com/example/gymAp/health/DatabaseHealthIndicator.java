//package com.example.gymAp.health;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabaseHealthIndicator implements HealthIndicator {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public Health health() {
//        try {
//            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
//            return Health.up().build();
//        } catch (Exception ex) {
//            return Health.down().withDetail("error", ex.getMessage()).build();
//        }
//    }
//}
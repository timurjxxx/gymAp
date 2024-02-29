package com.example.gymAp.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        Objects.requireNonNull(jdbcTemplate, "JdbcTemplate must not be null");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        try {
            // Execute a simple query to check database connectivity
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Health.up().withDetail("message", "Database is available").build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return Health.down().withDetail("error", "Unable to connect to the database").build();
        }
    }
}
package com.example.gymAp.actuator.metrics;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;


import io.micrometer.core.instrument.MeterRegistry;


@Component
public class DatabaseMetrics {

    @Autowired
    private DataSource dataSource;

    private final Counter failedQueries;
    private final Timer connectionTimer;

    @Autowired
    public DatabaseMetrics(MeterRegistry registry) {
        // Initialize metrics with Micrometer's registry
        this.failedQueries = Counter.builder("database_failed_queries")
                .description("Number of failed database queries")
                .register(registry);

        this.connectionTimer = Timer.builder("database_connection_time")
                .description("Time taken to acquire a database connection")
                .register(registry);
    }


    @PostConstruct
    public void initMetrics() {
        try (Connection connection = dataSource.getConnection()) {
            connectionTimer.record(() -> {
            });
        } catch (SQLException e) {
            failedQueries.increment();
        }
    }
}

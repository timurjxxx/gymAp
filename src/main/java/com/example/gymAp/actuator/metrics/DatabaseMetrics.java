//package com.example.gymAp.actuator.metrics;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.Timer;
//import javax.sql.DataSource;
//import org.springframework.stereotype.Component;
//
//import java.sql.SQLException;
//
//
//@Component
//public class DatabaseMetrics {
//
//    @Autowired
//    private DataSource dataSource;
//
//    private final Counter failedQueries = Counter
//            .builder("database_failed_queries")
//            .description("Number of failed database queries")
//            .build();
//
//    @PostConstruct
//    public void initMetrics() {
//        Timer connectionTimer = Timer
//                .builder("database_connection_time")
//                .description("Time taken to acquire a database connection")
//
//        // Measure time to acquire connection and increment failed queries on exceptions
//        dataSource.getConnection((connection) -> {
//            connectionTimer.record(() -> connection.close());
//        }, (SQLException e) -> failedQueries.increment());
//    }
//
//    // Expose metrics to Prometheus through Micrometer's Prometheus registry
//    // (configuration required)
//}

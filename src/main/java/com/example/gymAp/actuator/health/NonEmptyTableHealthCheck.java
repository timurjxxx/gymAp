package com.example.gymAp.actuator.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@Component
public class NonEmptyTableHealthCheck extends BaseHealthCheck {

    private final DataSource dataSource;
    private final String tableName;

    public NonEmptyTableHealthCheck(DataSource dataSource, @Value("${health.check.table-name:training_type}") String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
    }

    @Override
    protected Health checkHealth() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            int nonEmptyTables = connection.createStatement().executeQuery(
                    String.format("SELECT COUNT(*) FROM information_schema.tables WHERE TABLE_NAME = '%s'", tableName)
            ).getInt(1);
            if (nonEmptyTables == 0) {
                return Health.down().withDetail("reason", "Table '" + tableName + "' is empty").build();
            }
            return Health.up().build();
        }
    }
}

package com.example.gymAp.actuatorTest;
import com.example.gymAp.actuator.health.DiskSpaceHealthCheck;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DiskSpaceHealthCheckTest {


    @Test
    public void testCheckHealthUp() {
        // Arrange
        DiskSpaceHealthCheck healthCheck = new DiskSpaceHealthCheck();

        // Act
        Health result = healthCheck.checkHealth();

        // Assert
        assertEquals(Health.up().build(), result, "Health check should be UP when disk space is sufficient");
    }


}
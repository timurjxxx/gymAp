package com.example.gymAp.actuatorTest.metricsTest;

import com.example.gymAp.actuator.metrics.JvmMemoryMetrics;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JvmMemoryMetricsTest {

    @Test
    public void testJvmMemoryMetrics() {
        // Arrange
        MeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        JvmMemoryMetrics jvmMemoryMetrics = new JvmMemoryMetrics();

        // Act
        double memoryUsageValue = jvmMemoryMetrics.getMemoryUsage().value();

        // Assert
        assertEquals(Runtime.getRuntime().totalMemory(), memoryUsageValue, "Memory usage should match total memory");
    }
}

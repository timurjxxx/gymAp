//package com.example.gymAp.actuatorTest.metricsTest;
//import com.example.gymAp.actuator.metrics.DatabaseMetrics;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Timer;
//import io.micrometer.core.instrument.config.MeterRegistryConfig;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.mockito.Mockito.*;
//
//class DatabaseMetricsTest {
//
//    @Mock
//    private DataSource dataSource;
//
//    @Mock
//    private MeterRegistry meterRegistry;
//
//    @Mock
//    private Timer timer;
//
//    @Mock
//    private Counter counter;
//
//    @Mock
//    private MeterRegistryConfig meterRegistryConfig;
//
//    private DatabaseMetrics databaseMetrics;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Configure MeterRegistry to avoid null configuration
//        when(meterRegistry.config()).thenReturn(meterRegistryConfig);
//        when(meterRegistryConfig.pauseDetector()).thenReturn(mock(MeterRegistryConfig.PauseDetector.class));
//        when(meterRegistry.timer(anyString())).thenReturn(timer);
//        when(meterRegistry.counter(anyString())).thenReturn(counter);
//
//        // Set up meter filter to avoid null configuration
//        meterRegistry.config().meterFilter(MeterFilter.accept());
//
//        databaseMetrics = new DatabaseMetrics(meterRegistry);
//        databaseMetrics.dataSource = dataSource;
//    }
//
//    @Test
//    void initMetrics_ConnectionSuccess_TimerRecorded() throws SQLException {
//        // Arrange
//        Connection connection = mock(Connection.class);
//        when(dataSource.getConnection()).thenReturn(connection);
//
//        // Act
//        databaseMetrics.initMetrics();
//
//        // Assert
//        verify(timer, times(1)).record(any(Runnable.class));
//        verify(counter, never()).increment();
//    }
//
//    @Test
//    void initMetrics_ConnectionFailure_CounterIncremented() throws SQLException {
//        // Arrange
//        when(dataSource.getConnection()).thenThrow(new SQLException("Simulated SQL exception"));
//
//        // Act
//        databaseMetrics.initMetrics();
//
//        // Assert
//        verify(timer, never()).record(any(Runnable.class));
//        verify(counter, times(1)).increment();
//    }
//}

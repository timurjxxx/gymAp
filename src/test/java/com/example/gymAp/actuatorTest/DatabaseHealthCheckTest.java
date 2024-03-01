//package com.example.gymAp.actuatorTest;
//
//import com.example.gymAp.actuator.health.DatabaseHealthCheck;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.Status;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DatabaseHealthCheckTest {
//
//    @Mock
//    private DataSource dataSource;
//
//    @InjectMocks
//    private DatabaseHealthCheck databaseHealthCheck;
//
//    @Test
//    void checkHealth_WhenDatabaseConnectionIsValid_ShouldReturnUpStatus() throws SQLException {
//        Connection connection = mock(Connection.class);
//        when(dataSource.getConnection()).thenReturn(connection);
//        when(connection.isValid(0)).thenReturn(true);
//
//        Health result = databaseHealthCheck.checkHealth();
//
//        assertEquals(Status.UP, result.getStatus());
//        assertEquals(0, result.getDetails().size());
//
//        verify(dataSource, times(1)).getConnection();
//        verify(connection, times(1)).isValid(0);
//    }
//
//    @Test
//    void checkHealth_WhenDatabaseConnectionIsNotValid_ShouldReturnDownStatus() throws SQLException {
//        Connection connection = mock(Connection.class);
//        when(dataSource.getConnection()).thenReturn(connection);
//        when(connection.isValid(0)).thenReturn(false);
//
//        Health result = databaseHealthCheck.checkHealth();
//
//        assertEquals(Status.DOWN, result.getStatus());
//        assertEquals("Database connection not valid", result.getDetails().get("reason"));
//
//        verify(dataSource, times(1)).getConnection();
//        verify(connection, times(1)).isValid(0);
//    }
//
//    @Test
//    void checkHealth_WhenSQLExceptionThrown_ShouldReturnDownStatusWithDetails() throws SQLException {
//        when(dataSource.getConnection()).thenThrow(new SQLException("Simulated SQL exception"));
//
//        Health result = databaseHealthCheck.checkHealth();
//
//        assertEquals(Status.DOWN, result.getStatus());
//        assertEquals("Database connection not valid", result.getDetails().get("reason"));
//        assertEquals("Simulated SQL exception", result.getDetails().get("error"));
//
//        verify(dataSource, times(1)).getConnection();
//    }
//}

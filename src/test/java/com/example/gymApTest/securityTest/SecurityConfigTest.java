//package com.example.gymApTest.securityTest;
//
//import com.example.gymAp.config.SecurityConfig;
//import com.example.gymAp.security.JWTFilter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.mockito.Mockito.*;
//
//public class SecurityConfigTest {
//
//    @InjectMocks
//    private SecurityConfig securityConfig;
//
//    @Mock
//    private JWTFilter filter;
//
//    private HttpSecurity httpSecurity;
//
//    private SecurityFilterChain securityFilterChain;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize mocks
//        MockitoAnnotations.initMocks(this);
//
//        // Initialize HttpSecurity mock
//        httpSecurity = mock(HttpSecurity.class);
//    }
//
//    @Test
//    public void testFilterChain() throws Exception {
//        // Call the method
//        securityFilterChain = securityConfig.filterChain(httpSecurity);
//
//        // Verify method invocations
//        verify(httpSecurity).csrf(AbstractHttpConfigurer::disable);
//        verify(httpSecurity).cors(AbstractHttpConfigurer::disable);
//        verify(httpSecurity).authorizeHttpRequests(any());
//        verify(httpSecurity).sessionManagement(any());
//        verify(httpSecurity).exceptionHandling(any());
//        verify(httpSecurity).addFilterBefore(any(), any());
//    }
//}

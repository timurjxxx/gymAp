//package com.example.gymApTest.configTest;
//
//import com.example.gymAp.config.SecurityConfig;
//import com.example.gymAp.model.User;
//import com.example.gymAp.security.JWTFilter;
//import com.example.gymAp.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//
//public class SecurityConfigTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private JWTFilter filter;
//
//    @InjectMocks
//    private SecurityConfig securityConfig;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(securityConfig).build();
//    }
//
//
//
//    @Test
//    public void testDaoAuthenticationProvider() {
//        // Arrange
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        UserService mockedUserService = mock(UserService.class);
//        when(mockedUserService.loadUserByUsername("username")).thenReturn((UserDetails) new User());
//        securityConfig.userService(mockedUserService); // Inject the mock into the security config
//
//        // Act
//        DaoAuthenticationProvider provider = securityConfig.daoAuthenticationProvider();
//
//        // Assert
//        assertNotNull(provider);
//
//        Authentication authentication = provider.authenticate(new UsernamePasswordAuthenticationToken("username", "password"));
//        assertNotNull(authentication);
//    }
//
//    @Test
//    public void testPasswordEncoder() {
//        BCryptPasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
//        assertNotNull(passwordEncoder);
//
//        String encodedPassword = passwordEncoder.encode("password");
//        assertTrue(passwordEncoder.matches("password", encodedPassword));
//    }
//
//    // Add more test methods for other beans and configurations
//}

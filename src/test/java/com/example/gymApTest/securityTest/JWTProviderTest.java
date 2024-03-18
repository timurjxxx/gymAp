//package com.example.gymApTest.security;
//
//import com.example.gymAp.security.JWTProvider;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.security.Key;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class JWTProviderTest {
//
//    @Value("${app.jwt.access.expiration}")
//    private long ACCESS_EXPIRATION ;
//
//    @Mock
//    private Claims claims;
//
//    @Mock
//    private Key key;
//
//    @InjectMocks
//    private JWTProvider jwtProvider;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGenerateToken() {
//        // Arrange
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User("testUser", "password", Arrays.asList());
//
//        // Act
//        String token = jwtProvider.generateToken(userDetails);
//
//        // Assert
//        assertEquals(String.class, token.getClass());
//    }
//
//    @Test
//    public void testGetUsername() {
//        // Arrange
//        String username = "testUser";
//        when(claims.getSubject()).thenReturn(username);
//
//        // Act
//        String retrievedUsername = jwtProvider.getUsername("testToken");
//
//        // Assert
//        assertEquals(username, retrievedUsername);
//    }
//
//    @Test
//    public void testGetRoles() {
//        // Arrange
//        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
//        when(claims.get("roles", List.class)).thenReturn(roles);
//
//        // Act
//        List<String> retrievedRoles = jwtProvider.getRoles("testToken");
//
//        // Assert
//        assertEquals(roles, retrievedRoles);
//    }
//
//    @Test
//    public void testGetAllClaimsFromToken() {
//        // Arrange
//        JWTProvider jwtProvider = new JWTProvider();
//        Claims claims = mock(Claims.class);
//        String token = "testToken";
//
//        // Mock the behavior of parseSignedClaims
//        Jwts jwts = mock(Jwts.class);
//        when(jwts.parser().build().parseSignedClaims(token).getBody()).thenReturn(claims);
//
//        // Mock the behavior of Jwts.parser()
//        Jwts.pa parser = mock(Jwts.Parser.class);
//        when(parser.parseSignedClaims(token)).thenReturn(jwts);
//
//        // Mock the behavior of the JWTProvider's parserBuilder
//        JWTProvider spyJwtProvider = mock(JWTProvider.class);
//        when(spyJwtProvider.parserBuilder()).thenReturn(parser);
//
//        // Act
//        Claims retrievedClaims = spyJwtProvider.getAllClaimsFromToken(token);
//
//        // Assert
//        assertEquals(claims, retrievedClaims);
//    }
//}

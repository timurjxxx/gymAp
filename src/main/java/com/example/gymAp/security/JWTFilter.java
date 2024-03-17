//package com.example.gymAp.security;
//
//import com.example.gymAp.dao.UserDAO;
//import com.example.gymAp.model.User;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JWTFilter extends OncePerRequestFilter {
//    private final UserDAO userDAO;
//
//    private final JWTProvider jwtProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring("Bearer ".length());
//            if (jwtProvider.validRefreshToken(token)) {
//                String userId = jwtProvider.extractUserIdFromRefreshToken(token);
//                UsernamePasswordAuthenticationToken authentication = getAuthentication(userId);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(String username) {
//        User userDetails = userDAO.findUserByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("not found"));
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//}
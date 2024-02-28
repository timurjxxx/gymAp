package com.example.gymAp.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RestCallLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestCallLoggingAspect.class);

    @Pointcut("execution(* com.example.gymAp.controller.*.*(..))")
    public void restControllerMethods() {
    }

    @Before("restControllerMethods()")
    public void logRestCallDetails(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        LOGGER.info("REST Call: {} [{}]", request.getRequestURI(), request.getMethod());
        LOGGER.info("Class: {}, Method: {}", className, methodName);
        LOGGER.info("Request Parameters: {}", extractRequestParameters(request));
    }

    @AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void logRestCallResult(Object result) {
        LOGGER.info("REST Call Result: {}", extractResponseDetails(result));
    }

    public String extractRequestParameters(HttpServletRequest request) {
        StringBuilder parameters = new StringBuilder();
        request.getParameterMap().forEach((key, values) ->
                parameters.append(key).append("=").append(String.join(",", values)).append("; "));
        return parameters.toString();
    }

    public String extractResponseDetails(Object result) {
        if (result instanceof org.springframework.http.ResponseEntity) {
            org.springframework.http.ResponseEntity<?> responseEntity = (org.springframework.http.ResponseEntity<?>) result;
            return "Status: " + responseEntity.getStatusCodeValue() + ", Response Body: " + responseEntity.getBody();
        } else {
            return "Invalid response type";
        }
    }
}

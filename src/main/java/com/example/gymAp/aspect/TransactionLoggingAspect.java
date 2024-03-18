package com.example.gymAp.aspect;

import com.example.gymAp.utils.TransactionIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Aspect
@Component
@Slf4j
public class TransactionLoggingAspect {


    @AfterReturning(pointcut = "execution(* com.example.gymAp.service.*.*(..))", returning = "result")
    public void logTransaction(JoinPoint joinPoint, Object result) {
        String transactionId = TransactionIdGenerator.generateTransactionId();
        log.info("Transaction ID: {}", transactionId);
        log.debug("Detailed information about the transaction can be added here.");
    }
}

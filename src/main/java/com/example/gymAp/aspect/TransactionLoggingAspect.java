package com.example.gymAp.aspect;

import com.example.gymAp.utils.TransactionIdGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLoggingAspect.class);

    @AfterReturning(pointcut = "execution(* com.example.gymAp.service.*.*(..))", returning = "result")
    public void logTransaction(JoinPoint joinPoint, Object result) {
        String transactionId = TransactionIdGenerator.generateTransactionId();
        LOGGER.info("Transaction ID: {}", transactionId);
        LOGGER.debug("Detailed information about the transaction can be added here.");
    }
}

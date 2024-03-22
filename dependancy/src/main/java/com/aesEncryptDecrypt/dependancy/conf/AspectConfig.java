package com.aesEncryptDecrypt.dependancy.conf;
import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectConfig {
          private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    @Before("execution(* com.aesEncryptDecrypt.dependancy.controller.*.*(..))")
        public void logMethodCall(JoinPoint joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            Object[] methodArgs = joinPoint.getArgs();

            // Log method name and parameters
            logger.info("Method: " + methodName);
            logger.info("Parameters: " + Arrays.toString(methodArgs));
        }
//    @AfterReturning("execution(* com.aesEncryptDecrypt.dependancy.controller.*.*(..))")
//    public void logAfter(JoinPoint joinPoint, Object result) {
//        String methodName = joinPoint.getSignature().toShortString();
//        logger.info("Completed API method: " + methodName);
//        logger.info("Method returned: " + result);
//    }
    }



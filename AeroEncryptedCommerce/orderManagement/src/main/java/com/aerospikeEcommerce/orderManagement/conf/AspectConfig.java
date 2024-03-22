package com.aerospikeEcommerce.orderManagement.conf;
import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectConfig {
         private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    @Before("execution(* com.aerospikeEcommerce.orderManagement.controller.*.*(..))")
        public void logMethodCall(JoinPoint joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            Object[] methodArgs = joinPoint.getArgs();
            // Log method name and parameters
            System.out.println("Method: " + methodName);
            System.out.println("Parameters: " + Arrays.toString(methodArgs));
        }
    }



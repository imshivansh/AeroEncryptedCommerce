package com.aerospikeEcommerce.inventoryManagement.conf;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectConfig {

        @Before("execution(* com.aerospikeEcommerce.inventoryManagement.controller.*.*(..))")
        public void logMethodCall(JoinPoint joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            Object[] methodArgs = joinPoint.getArgs();

            // Log method name and parameters
            System.out.println("Method: " + methodName);
            System.out.println("Parameters: " + Arrays.toString(methodArgs));
        }
    }



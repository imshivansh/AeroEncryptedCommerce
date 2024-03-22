//package com.aerospikeEcommerce.orderManagement.conf;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//@Aspect
//@Component
//public class ApiLoggingAspect {
//;
//
//
//        private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//        @Before("execution(* com.example.controller.*.*(..))")
//        public void logBefore(JoinPoint joinPoint) {
//            String methodName = joinPoint.getSignature().toShortString();
//            logger.info("Calling API method: " + methodName);
//
//            Object[] args = joinPoint.getArgs();
//            if (args != null && args.length > 0) {
//                logger.info("Method parameters: ");
//            }
//        }
//
//        @AfterReturning("execution(* com.example.controller.*.*(..))")
//        public void logAfter(JoinPoint joinPoint, Object result) {
//            String methodName = joinPoint.getSignature().toShortString();
//            logger.info("Completed API method: " + methodName);
//            logger.info("Method returned: " + result);
//        }
//    }
//

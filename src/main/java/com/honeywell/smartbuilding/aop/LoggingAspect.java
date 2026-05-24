package com.honeywell.smartbuilding.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // This Pointcut intercepts ALL methods inside the "service" package
    @Around("execution(* com.honeywell.smartbuilding.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed(); // Let the actual method run

        long executionTime = System.currentTimeMillis() - start;
        
        logger.info("⚙️ [AOP PROFILER] {} executed in {} ms", joinPoint.getSignature(), executionTime);
        
        return proceed;
    }
}

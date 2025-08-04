package com.aome.todoist_mp_api.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(com.aome.todoist_mp_api.util.LoggableDebug)")
    public void logDebugBefore(JoinPoint joinPoint) {
        log.debug("→ {} called with args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "@annotation(com.aome.todoist_mp_api.util.LoggableDebug)", returning = "result")
    public void logDebugAfter(JoinPoint joinPoint, Object result) {
        log.debug("← {} returned {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @Before("@annotation(com.aome.todoist_mp_api.util.LoggableInfo)")
    public void logInfoBefore(JoinPoint joinPoint) {
        log.info("→ {} called with args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "@annotation(com.aome.todoist_mp_api.util.LoggableInfo)", returning = "result")
    public void logInfoAfter(JoinPoint joinPoint, Object result) {
        log.info("← {} returned {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(pointcut = "@annotation(com.aome.todoist_mp_api.util.LoggableDebug)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("✖ {} threw exception: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage(), ex);
    }
}

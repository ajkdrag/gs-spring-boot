package com.example.springboot.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.example.springboot.models.ExecTimeLogData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {
    Logger log = LoggerFactory.getLogger(ExecutionTimeAdvice.class);
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Around("@annotation(com.example.springboot.aspects.TrackExecutionTime)")
    public Object execTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        String elapsed = String.format("%s ms", System.currentTimeMillis() - startTime);

        ExecTimeLogData execTimeObj = new ExecTimeLogData();
        execTimeObj.setClassName(point.getSignature().getDeclaringTypeName());
        execTimeObj.setMethodName(point.getSignature().getName());
        execTimeObj.setElapsedTime(elapsed);

        log.info(gson.toJson(execTimeObj));

        return object;
    }

}

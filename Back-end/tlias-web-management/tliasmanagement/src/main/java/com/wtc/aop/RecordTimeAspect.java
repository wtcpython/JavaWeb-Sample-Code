package com.wtc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Aspect
@Component
public class RecordTimeAspect {

    @Around("execution(* com.wtc.service.impl.*.*(..))")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        // 记录开始时间
        long start = System.currentTimeMillis();

        // 执行原始方法
        Object result = pjp.proceed();

        // 记录结束时间，记录耗时
        long end = System.currentTimeMillis();
        log.info("方法 {} 方法耗时：{} ms", pjp.getSignature(), end - start);
        return result;
    }
}

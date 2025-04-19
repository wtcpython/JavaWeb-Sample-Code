package com.wtc.aop;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wtc.mapper.OperateLogMapper;
import com.wtc.pojo.OperateLog;
import com.wtc.utils.CurrentHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.wtc.anno.Log)")
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = pjp.proceed();

        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getCurrentUserId());
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(pjp.getTarget().getClass().getName());
        operateLog.setMethodName(pjp.getSignature().getName());
        operateLog.setMethodParams(Arrays.toString(pjp.getArgs()));
        operateLog.setReturnValue(result != null ? result.toString() : "void");
        operateLog.setCostTime(costTime);

        log.info("记录操作日志：{}", operateLog);
        operateLogMapper.insert(operateLog);

        return result;
    }

    private Integer getCurrentUserId() {
        return CurrentHolder.getCurrentId();
    }
}

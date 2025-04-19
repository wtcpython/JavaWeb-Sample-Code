package com.wtc.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Aspect
@Component
public class MyAspect {
    // 执行顺序：
    // Around(Before) -> Before -> Around(Proceed) -> AfterReturning/AfterThrowing -> After -> Around(After)

    // execution(访问修饰符? 返回值 报名.类名.方法名(参数列表) throws 异常?)
    @Pointcut("execution(* com.wtc.service.impl.*.*(..))")
    public void pointcut() {}

    /**
     * 前置通知 - 在目标方法运行之前执行
     */
    // @Before("execution(* com.wtc.service.impl.*.*(..))")
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("before ...");

        // 获取目标对象
        Object target = joinPoint.getTarget();
        log.info("获取目标对象: {}", target);

        // 获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        log.info("获取目标类: {}", targetClass);

        // 获取目标方法
        Object method = joinPoint.getSignature();
        log.info("获取目标方法: {}", method);

        // 获取目标方法参数
        Object[] args = joinPoint.getArgs();
        log.info("获取目标方法参数: {}", Arrays.toString(args));
    }

    /**
     * 环绕通知 - 在目标方法运行之前和之后执行
     * @param pjp
     * @return
     * @throws Throwable
     */
    // @Around("execution(* com.wtc.service.impl.*.*(..))")
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around ... before ...");
        Object result = pjp.proceed();
        log.info("around ... after ...");
        return result;
    }

    /**
     * 后置通知 - 在目标方法运行之后执行
     */
    // @After("execution(* com.wtc.service.impl.*.*(..))")
    @After("pointcut()")
    public void after() {
        log.info("after ...");
    }

    /**
     * 返回通知 - 在目标方法返回之后执行
     */
    // @AfterReturning("execution(* com.wtc.service.impl.*.*(..))")
    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("afterReturning ...");
    }

    /**
     * 异常通知 - 在目标方法抛出异常时执行
     */
    // @AfterThrowing("execution(* com.wtc.service.impl.*.*(..))")
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("afterThrowing ...");
    }
}

package com.scnu.ribbon.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Pointcut("execution(* com.scnu..*.*(..))")
    public void aspectMethod(){

    }

    @Around("aspectMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        try{
            Object[] args = point.getArgs();
            return point.proceed(args);
        }catch (Exception e){
            log.error("",e);
        }
        return  null;


    }

}

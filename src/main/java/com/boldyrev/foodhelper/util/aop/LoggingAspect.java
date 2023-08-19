package com.boldyrev.foodhelper.util.aop;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("within(com.boldyrev.foodhelper.services..*)")
    private void allServicesMethods() {
    }

//    @Pointcut("execution(public java.lang.Iterable findAll*(..))")
//    private void onlyFindAllMethods(){};

    @Around("allServicesMethods()")
    public Object logExecutionTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        log.debug("Method: {} - starts. Args: {}", joinPoint.getSignature(),
            Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.debug("Method:{} - ends. Args: {}, Execution time: {}", joinPoint.getSignature(),
            Arrays.toString(joinPoint.getArgs()), System.currentTimeMillis() - start);

        return result;
    }

//    @AfterReturning(pointcut = "allServicesMethods())", returning = "data", argNames = "data,joinPoint")
//    public void logEntityExistsOrNotFoundAdvice(Iterable data, JoinPoint joinPoint) {
//        if ((data instanceof List list && list.isEmpty()) || (data instanceof Page page
//            && page.isEmpty())) {
//            log.debug("Cant find data by method: {}, with args: {}", joinPoint.getSignature(),
//                Arrays.toString(joinPoint.getArgs()));
//            throw new EmptyDataException("Data not found by this parameters");
//        }
//    }

}

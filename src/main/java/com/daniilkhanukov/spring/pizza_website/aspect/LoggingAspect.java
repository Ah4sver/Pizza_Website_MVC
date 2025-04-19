package com.daniilkhanukov.spring.pizza_website.aspect;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.daniilkhanukov.spring.pizza_website.controller.*.*(..))")
    public void logController(JoinPoint joinPoint) {
        logger.info("→ {} args = {}",
                joinPoint.getSignature().toShortString(),
                Arrays.deepToString(joinPoint.getArgs()));
    }

    @AfterReturning(
            pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.*.*(..))",
            returning = "result")
    public void logControllerReturn(JoinPoint joinPoint, Object result) {
        logger.info("← {} returned = {}",
                joinPoint.getSignature().toShortString(), result);
    }

    // Логируем только методы сервисного слоя для мониторинга времени
    @Around("execution(* com.daniilkhanukov.spring.pizza_website.service.*.*(..))")
    public Object logServicePerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object ret = proceedingJoinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;
        logger.info("🕒 {} executed in {} ms",
                proceedingJoinPoint.getSignature().toShortString(), elapsed);
        return ret;
    }


//    // 1. Логируем только контроллеры и сервисы, исключая репозитории
//    @Pointcut("within(com.daniilkhanukov.spring.pizza_website.controller..*) || " +
//            "within(com.daniilkhanukov.spring.pizza_website.service..*)")
//    public void applicationLayer() {}
//
//    // 2. Перед выполнением метода — логируем метод и только значимые аргументы
//    @Before("applicationLayer()")
//    public void logBefore(JoinPoint joinPoint) {
//        String method = joinPoint.getSignature().toShortString();
//        // Фильтруем ненужные типы аргументов
//        String args = Arrays.stream(joinPoint.getArgs())
//                .filter(arg -> !(arg instanceof HttpSession)
//                        && !(arg instanceof Principal)
//                        && !(arg instanceof Authentication))
//                .map(Object::toString)
//                .collect(Collectors.joining(", ", "[", "]"));
//        logger.info("→ {} args = {}", method, args);
//    }
//
//    // 3. После успешного выполнения — логируем возвращаемое значение
//    @AfterReturning(pointcut = "applicationLayer()", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        String method = joinPoint.getSignature().toShortString();
//        String repr = result != null ? result.toString() : "null";
//        logger.info("← {} returned = {}", method, repr);
//    }
//
//    // 4. Засекаем время выполнения для сервисов
//    @Around("within(com.daniilkhanukov.spring.pizza_website.service..*)")
//    public Object logTiming(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object res = proceedingJoinPoint.proceed();
//        long elapsed = System.currentTimeMillis() - start;
//        String method = proceedingJoinPoint.getSignature().toShortString();
//        logger.info("🕒 {} executed in {} ms", method, elapsed);
//        return res;
//    }

    // === Pointcuts ===
//
//    @Pointcut("within(com.daniilkhanukov.spring.pizza_website.controller..*)")
//    public void controllerLayer() {}
//
    @Pointcut("within(com.daniilkhanukov.spring.pizza_website.service..*)")
    public void serviceLayer() {}
//
//    @Pointcut("within(com.daniilkhanukov.spring.pizza_website.repository..*)")
//    public void repositoryLayer() {}
//
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}
//
//    // === Advices ===
//
//    // 1. Логирование контроллеров
//    @Before("execution(* com.daniilkhanukov.spring.pizza_website..*(..))")
//    public void logBefore(JoinPoint joinPoint) {
//        String method = joinPoint.getSignature().toShortString();
//        String args = Arrays.deepToString(joinPoint.getArgs());
//        logger.info("→ {} args = {}", method, args);
//    }
//
//    @Before("controllerLayer()")
//    public void logBeforeController(JoinPoint joinPoint) {
//        logger.info("HTTP Request → {}.{}() args = {}",
//                joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(),
//                Arrays.toString(joinPoint.getArgs()));
//    }
//
//    // 2. Логирование успешных сервис-методов
//    @AfterReturning(pointcut="serviceLayer()", returning="result")
//    public void logAfterService(JoinPoint joinPoint, Object result) {
//        logger.info("Service Response ← {}.{}() returned = {}",
//                joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(),
//                result);
//    }
//
    // 3. Обработка исключений в сервис-слое
    @AfterThrowing(pointcut="serviceLayer()", throwing="ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Service Exception ❌ {}.{}() cause = {}, message = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getCause(), ex.getMessage());
    }
//
//    // 4. Замер производительности сервис-методов
//    @Around("serviceLayer()")
//    public Object logAroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object result = proceedingJoinPoint.proceed();
//        long elapsed = System.currentTimeMillis() - start;
//        if (logger.isInfoEnabled()) {
//            logger.info("Service Response ← {} returned = {}",
//                    proceedingJoinPoint.getSignature().toShortString(),
//                    result);
//            logger.info("Performance 🕒 {} executed in {} ms",
//                    proceedingJoinPoint.getSignature().toShortString(),
//                    elapsed);
//        }
//        return result;
//    }
//
//
    // 5. Транзакционный аудит
    @Before("transactionalMethods()")
    public void logBeforeTx(JoinPoint joinPoint) {
        logger.info("TX START ⏳ {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @After("transactionalMethods()")
    public void logAfterTx(JoinPoint joinPoint) {
        logger.info("TX END ✅ {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }
//
//    // 6. Логирование репозиториев (необязательно)
//    @Before("repositoryLayer()")
//    public void logBeforeRepo(JoinPoint joinPoint) {
//        logger.debug("Repository Call → {}.{}()",
//                joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName());
//    }

}

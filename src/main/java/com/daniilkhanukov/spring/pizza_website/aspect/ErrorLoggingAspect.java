package com.daniilkhanukov.spring.pizza_website.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
class ErrorLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ErrorLoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller..*(..))", throwing = "ex")
    public void logControllerErrors(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String url = attributes != null ? attributes.getRequest().getRequestURI() : "неизвестный URL";
        logger.error("Ошибка в {}.{} (URL: {}): {}", className, methodName, url, ex.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.service..*(..))", throwing = "ex")
    public void logServiceErrors(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.error("Ошибка в сервисе {}.{}: {}", className, methodName, ex.getMessage());
    }
}

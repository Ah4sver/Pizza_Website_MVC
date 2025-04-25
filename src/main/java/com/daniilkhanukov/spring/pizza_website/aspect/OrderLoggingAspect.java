package com.daniilkhanukov.spring.pizza_website.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;


@Aspect
@Component
class OrderLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(OrderLoggingAspect.class);


    @After("execution(* com.daniilkhanukov.spring.pizza_website.controller.CartController.createOrder(..))")
    public void logOrderCreated(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        String address = joinPoint.getArgs()[0].toString();
        logger.info("Пользователь '{}' оформил заказ на адрес {}", username, address);
    }


    @AfterThrowing(
            pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.CartController.createOrder(..))",
            throwing = "ex")
    public void logOrderFailed(JoinPoint joinPoint, Throwable ex) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        logger.warn("Не удалось оформить заказ для '{}': {}", username, ex.getMessage());
    }

}

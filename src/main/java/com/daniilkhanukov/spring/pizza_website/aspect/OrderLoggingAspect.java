package com.daniilkhanukov.spring.pizza_website.aspect;

import com.daniilkhanukov.spring.pizza_website.entity.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
class OrderLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(OrderLoggingAspect.class);


    @AfterReturning(
            pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.service.OrderServiceImpl.save(..))",
            returning = "order")
    public void logOrderCreated(JoinPoint joinPoint, Order order) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        logger.info("Пользователь '{}' оформил заказ с id={}", username, order.getId());
    }

    @AfterThrowing(
            pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.service.OrderServiceImpl.save(..))",
            throwing = "ex")
    public void logOrderFailed(JoinPoint joinPoint, Throwable ex) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : "аноним";
        logger.warn("Не удалось оформить заказ для '{}': {}", username, ex.getMessage());
    }

}

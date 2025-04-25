package com.daniilkhanukov.spring.pizza_website.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
class ManagementLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ManagementLoggingAspect.class);

    @AfterReturning(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.ManagerController.deleteOrder(..))", returning = "result")
    public void logOrderStatusUpdate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Integer) {
            Integer orderId = (Integer) args[0];
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                logger.info("Менеджер {} удалил заказ с id={}", auth.getName(), orderId);
            }
        } else {
            logger.warn("Не удалось извлечь orderId из аргументов: {}", Arrays.toString(args));
        }
    }

    @AfterThrowing(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.ManagerController.deleteOrder(..))", throwing = "ex")
    public void logFailedOrderStatusUpdate(JoinPoint joinPoint, Throwable ex) {
        Object[] args = joinPoint.getArgs();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        logger.warn("Не удалось обновить статус заказа для менеджера {}: {}", auth.getName(), ex.getMessage());
    }

    @After("execution(* com.daniilkhanukov.spring.pizza_website.controller.ManagerController.togglePizzaAvailability(..))")
    public void logTogglePizzaAvailability(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer pizzaId = (Integer) args[0];

        logger.info("Менеджер '{}' изменил статус наличия пиццы с id={}", auth.getName(), pizzaId);
    }

    @After("execution(* com.daniilkhanukov.spring.pizza_website.controller.ManagerController.changePizzaPrice(..))")
    public void logChangePizzaPrice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer pizzaId = (Integer) args[0];
        Double pizzaPrice = (Double) args[1];

        logger.info("Менеджер '{}' изменил цену пиццы с id={}, теперь цена = {}", auth.getName(), pizzaId, pizzaPrice);
    }


}

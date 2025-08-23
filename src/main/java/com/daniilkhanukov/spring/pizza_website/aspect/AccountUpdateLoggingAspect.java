package com.daniilkhanukov.spring.pizza_website.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Aspect
@Component
class AccountUpdateLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(AccountUpdateLoggingAspect.class);


    @AfterReturning(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.AccountController.updateAccountDetails(..))", returning = "result")
    public void logSuccessfulAccountUpdate(JoinPoint joinPoint, Object result) {
        if ("redirect:/account".equals(result)) {
            Object[] args = joinPoint.getArgs();
            Authentication auth = extractAuthenticationFromArgs(args);
            if (auth != null) {
                logger.info("Пользователь {} обновил данные аккаунта", auth.getName());

            }
        }
    }

    @AfterThrowing(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.AccountController.updateAccountDetails(..))", throwing = "ex")
    public void logFailedAccountUpdate(JoinPoint joinPoint, Throwable ex) {
        Object[] args = joinPoint.getArgs();
        Authentication auth = extractAuthenticationFromArgs(args);
        String username = auth != null ? auth.getName() : "неизвестного пользователя";
        logger.warn("Не удалось обновить данные для {}: {}", username, ex.getMessage());
    }

    private Authentication extractAuthenticationFromArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Authentication) {
                return (Authentication) arg;
            }
        }
        return null;
    }
}

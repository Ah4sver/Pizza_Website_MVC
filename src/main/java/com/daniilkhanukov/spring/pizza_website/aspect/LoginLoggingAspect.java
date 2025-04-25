package com.daniilkhanukov.spring.pizza_website.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Aspect
@Component
class LoginLoggingAspect implements ApplicationListener<AbstractAuthenticationEvent> {
    private static final Logger logger = LoggerFactory.getLogger(LoginLoggingAspect.class);

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent success) {
            String username = success.getAuthentication().getName();
            logger.info("Пользователь '{}' успешно вошёл в систему", username);
        }
        else if (event instanceof AbstractAuthenticationFailureEvent failure) {
            String username = Optional.ofNullable(failure.getAuthentication().getName())
                    .orElse("неизвестный");
            String reason = failure.getException().getMessage();
            logger.warn("Неудачная попытка входа для '{}'. Причина: {}", username, reason);
        }
    }

//    @AfterReturning(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.AccountController.login(..))", returning = "result")
//    public void logSuccessfulLogin(JoinPoint joinPoint, Object result) {
//        if ("redirect:/home".equals(result)) {
//            Object[] args = joinPoint.getArgs();
//            String username = extractUsernameFromArgs(args);
//            if (username != null) {
//                logger.info("Пользователь {} вошел в систему", username);
//            }
//        }
//    }
//
//    @AfterThrowing(pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.AccountController.login(..))", throwing = "ex")
//    public void logFailedLogin(JoinPoint joinPoint, Throwable ex) {
//        Object[] args = joinPoint.getArgs();
//        String username = extractUsernameFromArgs(args);
//        logger.warn("Неудачная попытка входа для {}: {}", username != null ? username : "неизвестного пользователя", ex.getMessage());
//    }
//
//    private String extractUsernameFromArgs(Object[] args) {
//        for (Object arg : args) {
//            if (arg instanceof String && arg.toString().contains("@")) {
//                return (String) arg;
//            }
//        }
//        return null;
//    }
}

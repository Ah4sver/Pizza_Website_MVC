package com.daniilkhanukov.spring.pizza_website.aspect;

import com.daniilkhanukov.spring.pizza_website.dto.UserRegistrationDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RegistrationLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationLoggingAspect.class);

    @AfterReturning(
            pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.AccountController.register(..))",
            returning = "result"
    )
    public void logSuccessfulRegistration(JoinPoint joinPoint, Object result) {
        if ("redirect:/login".equals(result)) {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof UserRegistrationDTO dto) {
                    String email = dto.getEmail();
                    logger.info("Пользователь {} успешно зарегистрирован", email);
                    break;
                }
            }
        }
    }

    @AfterThrowing(
            pointcut = "execution(* com.daniilkhanukov.spring.pizza_website.controller.AccountController.register(..))",
            throwing = "ex"
    )
    public void logFailedRegistration(JoinPoint joinPoint, Throwable ex) {
        Object[] args = joinPoint.getArgs();
        String email = "неизвестного пользователя";
        for (Object arg : args) {
            if (arg instanceof UserRegistrationDTO dto) {
                email = dto.getEmail();
                break;
            }
        }
        logger.warn("Неудачная регистрация для {}: {}", email, ex.getMessage());
    }


}
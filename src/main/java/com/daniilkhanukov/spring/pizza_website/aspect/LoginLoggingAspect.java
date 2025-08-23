package com.daniilkhanukov.spring.pizza_website.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}

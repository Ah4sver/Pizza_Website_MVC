package com.daniilkhanukov.spring.pizza_website.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.daniilkhanukov.spring.pizza_website")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.daniilkhanukov.spring.pizza_website.repository")
@EntityScan(basePackages = "com.daniilkhanukov.spring.pizza_website.entity")
public class MyConfig {

}
